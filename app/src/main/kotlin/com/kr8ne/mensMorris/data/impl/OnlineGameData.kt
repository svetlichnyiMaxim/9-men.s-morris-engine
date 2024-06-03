package com.kr8ne.mensMorris.data.impl

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.ONLINE_GAME_SCREEN
import com.kr8ne.mensMorris.Position
import com.kr8ne.mensMorris.WELCOME_SCREEN
import com.kr8ne.mensMorris.api.Client
import com.kr8ne.mensMorris.data.interfaces.DataModel
import com.kr8ne.mensMorris.data.interfaces.GameBoardInterface
import com.kr8ne.mensMorris.move.Movement
import com.kr8ne.mensMorris.viewModel.impl.GameBoardViewModel
import com.kroune.MoveResponse
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
    private val gameId: Long,
    val navController: NavHostController?
) : GameBoardInterface, DataModel {

    private var isGreen: Boolean? = null
    override val gameBoard = GameBoardViewModel(
        onClick = { index, func -> response(index, func) },
        navController = null
    )

    private fun response(index: Int, func: (index: Int) -> Unit) {
        // check if we can make this move
        if (isGreen == true && gameBoard.data.pos.value.pieceToMove || isGreen == false && !gameBoard.data.pos.value.pieceToMove) {
            gameBoard.data.getMovement(index)?.let {
                Client.movesQueue.add(it)
            }
            func(index)
        }
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
                    isGreen =
                        (Json.decodeFromString<MoveResponse>((incoming.receive() as Frame.Text).readText())).message.toBoolean()
                    gameBoard.data.pos.value =
                        Json.decodeFromString<Position>(Json.decodeFromString<MoveResponse>((incoming.receive() as Frame.Text).readText()).message!!)
                    while (true) {
                        // send all our moves one by one
                        if (Client.movesQueue.isNotEmpty()) {
                            val moveToSend = Client.movesQueue.poll()!!
                            val string = Json.encodeToString<Movement>(moveToSend)
                            // post our move
                            println(string)
                            send(string)
                        }
                        try {
                            // receive the server's data
                            val serverMessage =
                                incoming.tryReceive().getOrNull() as? Frame.Text ?: continue
                            println(serverMessage)
                            val serverResponse =
                                Json.decodeFromString<MoveResponse>(serverMessage.readText())
                            when (serverResponse.code) {
                                410 -> {
                                    // game ended
                                    println("game ended")
                                    navController?.navigate(WELCOME_SCREEN)
                                }

                                200 -> {
                                    val movement =
                                        Json.decodeFromString<Movement>(serverResponse.message!!)
                                    // apply move
                                    gameBoard.data.pos.value =
                                        movement.producePosition(gameBoard.data.pos.value)
                                }

                                else -> {
                                    // we reload our game
                                    navController?.navigate(ONLINE_GAME_SCREEN)
                                    println("smth went wrong, reloading")
                                }
                            }
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
