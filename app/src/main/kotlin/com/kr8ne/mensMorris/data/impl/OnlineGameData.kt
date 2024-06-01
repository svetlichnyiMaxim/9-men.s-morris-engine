package com.kr8ne.mensMorris.data.impl

import androidx.compose.runtime.mutableStateOf
import com.kr8ne.mensMorris.api.Client
import com.kr8ne.mensMorris.api.MovementAdapter
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
import okhttp3.WebSocket

/**
 * data for online game screen
 */
class OnlineGameData(
    private val gameId: Long
) : GameBoardInterface, DataModel {

    val position = mutableStateOf(gameStartPosition)
    override val gameBoard = GameBoardViewModel(
        pos = position,
        onClick = { index, func -> response(index, func) },
        navController = null
    )

    private fun response(index: Int, func: (index: Int) -> Unit) {
        gameBoard.data.getMovement(index)?.let {
            Client.movesQueue.add(it)
        }
        println("move added to the queue")
        println(gameBoard.data.pos.value.toString())
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
                        while (Client.movesQueue.isNotEmpty()) {
                            val string = Json.encodeToString<MovementAdapter>(
                                MovementAdapter(
                                    Client.movesQueue.peek()!!.startIndex,
                                    Client.movesQueue.peek()!!.endIndex
                                )
                            )
                            // post our move
                            println("sent move $string")
                            send(string)
                            Client.movesQueue.remove()
                        }
                        try {
                            // receive the server's data
                            val serverMessage =
                                incoming.tryReceive().getOrNull() as? Frame.Text ?: continue
                            println(serverMessage.readText())
                            val move = Json.decodeFromString<MovementAdapter>(serverMessage.readText())
                                    .toMovement()
                            println("received move")
                            gameBoard.data.processMove(move)
                            println(gameBoard.data.pos.value.toString())
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

fun MovementAdapter.toMovement(): Movement {
    return Movement(
        startIndex = this.startIndex,
        endIndex = this.endIndex
    )
}