package com.kr8ne.mensMorris.data.impl

import androidx.compose.runtime.mutableStateOf
import com.kr8ne.mensMorris.api.Client
import com.kr8ne.mensMorris.common.game.Movement
import com.kr8ne.mensMorris.common.game.utils.gameStartPosition
import com.kr8ne.mensMorris.data.interfaces.DataModel
import com.kr8ne.mensMorris.data.interfaces.GameBoardInterface
import com.kr8ne.mensMorris.viewModel.impl.GameBoardViewModel
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.utils.io.printStack
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * data for online game screen
 */
class OnlineGameData(
    private val gameId: Long
) : GameBoardInterface, DataModel {

    override val gameBoard = GameBoardViewModel(
        onClick = { index, func -> response(index, func) },
        navController = null
    )

    private fun response(index: Int, func: (index: Int) -> Unit) {
        gameBoard.data.getMovement(index)?.let {
            Client.movesQueue.add(it)
        }
        func(index)
    }

    override suspend fun invokeBackend() {
        println("invoke")
        Client.playingGameJob?.cancel()
        Client.playingGameJob = CoroutineScope(Client.networkScope).async {
            runCatching {
                val jwtTokenState = Client.jwtToken
                require(jwtTokenState != null)
                Client.network.webSocket(
                    "ws${Client.SERVER_ADDRESS}${Client.USER_API}/game",
                    request = {
                        url {
                            parameters["jwtToken"] = jwtTokenState
                            parameters["gameId"] = gameId.toString()
                        }
                    }) {
                    while (true) {
                        // send all our moves
                        while (Client.movesQueue.isNotEmpty()) {
                            val string = Json.encodeToString<Movement>(Client.movesQueue.peek()!!)
                            // post our move
                            send(string)
                            Client.movesQueue.remove()
                        }
                        try {
                            // receive the server's data
                            val serverMessage =
                                incoming.tryReceive().getOrNull() as? Frame.Text ?: continue
                            val move = Json.decodeFromString<Movement>(serverMessage.readText())
                            println("received move")
                            gameBoard.data.processMove(move)
                        } catch (e: Exception) {
                            println("error when receiving move")
                            e.printStackTrace()
                        }
                    }
                }
            }.onFailure {
                println("error accessing ${"${Client.SERVER_ADDRESS}${Client.USER_API}/game; gameId = $gameId"}")
                it.printStack()
            }
        }
        Client.playingGameJob?.start()
    }
}
