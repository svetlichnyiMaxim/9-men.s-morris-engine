package com.kroune.nineMensMorrisApp.viewModel.impl.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kr8ne.mensMorris.Position
import com.kr8ne.mensMorris.gameStartPosition
import com.kr8ne.mensMorris.move.Movement
import com.kroune.GameResponse
import com.kroune.GameSignals
import com.kroune.nineMensMorrisApp.common.SERVER_ADDRESS
import com.kroune.nineMensMorrisApp.common.USER_API
import com.kroune.nineMensMorrisApp.data.local.impl.game.GameBoardData
import com.kroune.nineMensMorrisApp.data.local.impl.game.OnlineGameData
import com.kroune.nineMensMorrisApp.data.remote.Common.network
import com.kroune.nineMensMorrisApp.data.remote.account.AccountInfoRepositoryI
import com.kroune.nineMensMorrisApp.data.remote.game.GameRepositoryI
import com.kroune.nineMensMorrisApp.ui.impl.game.GameBoardScreen
import com.kroune.nineMensMorrisApp.viewModel.interfaces.ViewModelI
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.utils.io.printStack
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * welcome model
 * called when app is launched
 */
class OnlineGameViewModel @AssistedInject constructor(
    private val accountInfoRepositoryI: AccountInfoRepositoryI,
    private val gameRepository: GameRepositoryI,
    @Assisted
    private val gameId: Long
) : ViewModelI() {
    /**
     * assisted factory for [OnlineGameViewModel]
     */
    @AssistedFactory
    interface AssistedVMFactory {
        /**
         * creates [OnlineGameViewModel] using id
         */
        fun create(gameId: Long): OnlineGameViewModel
    }

    companion object {
        /**
         * provides factory
         */
        fun provideFactory(
            assistedVMFactory: AssistedVMFactory,
            id: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return assistedVMFactory.create(id) as T
            }
        }
    }

    override val data = OnlineGameData()

    private val counter = 0

    private suspend fun DefaultClientWebSocketSession.get(): String {
        val text = (incoming.receive() as Frame.Text).readText()
        val serverResponse = Json.decodeFromString<GameResponse>(text)
        if (counter + 1 != serverResponse.counter) {
            this.close(CloseReason(409, "data desync"))
            // we restart our connection
            connectToTheGameAndPlay()
        }
        return serverResponse.message
    }

    private suspend fun DefaultClientWebSocketSession.tryGet(): String? {
        val text = (incoming.tryReceive().getOrNull() as? Frame.Text)?.readText() ?: return null
        val serverResponse = Json.decodeFromString<GameResponse>(text)
        if (counter + 1 != serverResponse.counter) {
            this.close(CloseReason(409, "data desync"))
            // we restart our connection
            connectToTheGameAndPlay()
        }
        return serverResponse.message
    }

    private var gameJob: Job? = null

    private fun connectToTheGameAndPlay() {
        gameJob?.cancel()
        gameJob = viewModelScope.launch {
            try {
                // it is used to make sure there is no data desync
                val jwtTokenState =
                    accountInfoRepositoryI.jwtTokenState.value ?: error("jwt token cannot be null")
                network.webSocket("ws$SERVER_ADDRESS$USER_API/game",
                    request = {
                        url {
                            parameters["jwtToken"] = jwtTokenState
                            parameters["gameId"] = gameId.toString()
                        }
                    }) {
                    isGreen = get().toBoolean()
                    gameBoard.pos.value = Json.decodeFromString<Position>(get())

                    while (true) {
                        // send all our moves one by one
                        val moveToSend = gameRepository.movesQueue.poll()
                        if (moveToSend != null) {
                            val string = Json.encodeToString<Movement>(moveToSend)
                            // post our move
                            send(string)
                        }
                        // receive the server's data
                        val serverMessage = Json.decodeFromString<GameSignals>(
                            tryGet() ?: continue
                        )
                        when (serverMessage) {
                            is GameSignals.GameEnd -> {
                                // game ended
                                println("game ended")
                                this.close(CloseReason(200, "game ended"))
                                gameJob?.cancel()
                            }

                            is GameSignals.Move -> {
                                val movement =
                                    Json.decodeFromString<Movement>(serverMessage.message)
                                // apply move
                                println("new move")
                                gameBoard.viewModel.data.processMove(movement)
                            }

                            else -> {
                                // we reload our game
                                this.close(CloseReason(409, "data desync"))
                                // we restart our connection
                                connectToTheGameAndPlay()
                                println("smth went wrong, reloading")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                println("error accessing ${"$SERVER_ADDRESS$USER_API/game; gameId = $gameId"}")
                e.printStack()
                // we try to relaunch this shit
                connectToTheGameAndPlay()
            }
        }
    }


    /**
     * tells if user is green or blue
     */
    private var isGreen: Boolean? = null
        set(value) {
            _uiState.value = _uiState.value.copy(isGreen = value)
            field = value
        }

    /**
     * our game board
     */
    private val gameBoard = GameBoardScreen(
        pos = gameStartPosition,
        onClick = { index -> this.response(index) },
        navController = null
    )

    private fun GameBoardData.response(index: Int) {
        // check if we can make this move
        println("isGreen - $isGreen; pieceToMove - ${gameBoard.pos.value.pieceToMove}")
        if (isGreen == gameBoard.pos.value.pieceToMove) {
            gameBoard.viewModel.data.getMovement(index)?.let {
                println("added move")
                gameRepository.movesQueue.add(it)
            }
            handleClick(index)
            handleHighLighting()
        }
    }

    private val _uiState =
        MutableStateFlow(OnlineGameScreenUiState(gameBoard, isGreen))

    /**
     * exposed ui state
     */
    val uiState: StateFlow<OnlineGameScreenUiState>
        get() = _uiState

    init {
        connectToTheGameAndPlay()
    }
}

/**
 * ui state
 */
data class OnlineGameScreenUiState(
    /**
     * game board
     */
    val gameBoard: GameBoardScreen,
    /**
     * is green status
     */
    val isGreen: Boolean?
)
