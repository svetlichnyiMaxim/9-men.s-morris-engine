package com.kr8ne.mensMorris.data.remote

import androidx.core.content.edit
import com.kr8ne.mensMorris.activity
import com.kr8ne.mensMorris.common.SERVER_ADDRESS
import com.kr8ne.mensMorris.common.USER_API
import com.kr8ne.mensMorris.move.Movement
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.utils.io.printStack
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import java.util.concurrent.ConcurrentLinkedQueue

object Game {
    /**
     * Starts searching for a game.
     *
     * @return [ServerResponse] indicating the success or failure of the search attempt.
     */
    fun startSearchingGame() {
        if (searchingForGameJob?.isCompleted == false) {
            return
        }
        searchingForGameJob = CoroutineScope(networkScope).async {
            runCatching {
                val jwtTokenState = Auth.jwtToken
                require(jwtTokenState != null)
                var gameId: String? = null
                network.webSocket("ws$SERVER_ADDRESS$USER_API/search-for-game", request = {
                    url {
                        parameters["jwtToken"] = jwtTokenState
                    }
                }) {
                    while (true) {
                        val serverMessage = (incoming.receive() as? Frame.Text)?.readText()
                        if (serverMessage != null) {
                            println("game id: $serverMessage")
                            gameId = serverMessage
                            close(CloseReason(CloseReason.Codes.NORMAL, "ok"))
                            break
                        }
                    }
                }
                gameId!!.toLong()
            }.onFailure {
                println("error accessing ${"ws$SERVER_ADDRESS$USER_API/search-for-game"}")
                it.printStack()
            }
        }
    }

    /**
     * waits for game searching result
     */
    suspend fun awaitForGameSearchEnd(): Result<Long>? {
        return searchingForGameJob?.await()
    }


    /**
     * current game id
     */
    @Volatile
    var gameId: Long? = activity?.sharedPreferences?.getString("gameId", null)?.toLongOrNull()
        set(value) {
            field = value
            activity?.sharedPreferences?.edit(commit = true) {
                putString("gameId", value.toString()).apply()
            }
        }

    /**
     * job created when searching for a game
     */
    var searchingForGameJob: Deferred<Result<Long>>? = null

    /**
     * queue of the moves that player performed
     * TODO: implement pre-moves with this one
     */
    val movesQueue = ConcurrentLinkedQueue<Movement>()

    /**
     * checks if we are currently playing a game
     */
    suspend fun isPlaying(): Result<Long?> {
        return runCatching {
            val jwtTokenState = Auth.jwtToken
            require(jwtTokenState != null)
            val result = network.get("http$SERVER_ADDRESS$USER_API/is-playing") {
                method = HttpMethod.Get
                url {
                    parameters["jwtToken"] = jwtTokenState
                }
            }
            result.bodyAsText().toLongOrNull()
        }.onFailure {
            println("error accessing ${"http$SERVER_ADDRESS$USER_API/is-playing"}")
            it.printStack()
        }
    }
}