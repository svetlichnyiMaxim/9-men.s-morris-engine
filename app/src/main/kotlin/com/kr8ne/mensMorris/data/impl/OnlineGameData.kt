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
        /**
         * if you even see this debug message in logs more than once you should know
         * that you are FUCKED
         * I have tried fixing this complete piece of shit for MANY hours
         * I have made a "fix"
         * but it is the shittiest piece of code I have written in more than 30 days
         * I don't know what causes this issue
         * I don't want to debug it
         * I don't want to touch it
         * YOU don't want to touch it, leave it as it is, don't waste your life
         * I hope it will never break or at least I won't be the one responsible for it
         * FUCK THIS SHIT
         */
        println("invoke")
        CoroutineScope(Client.networkScope).async {
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
    }
}
