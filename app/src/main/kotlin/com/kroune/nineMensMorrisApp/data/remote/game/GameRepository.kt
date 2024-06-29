package com.kroune.nineMensMorrisApp.data.remote.game

import com.kr8ne.mensMorris.move.Movement
import com.kroune.nineMensMorrisApp.common.SERVER_ADDRESS
import com.kroune.nineMensMorrisApp.common.USER_API
import com.kroune.nineMensMorrisApp.data.remote.Common.jwtToken
import com.kroune.nineMensMorrisApp.data.remote.Common.network
import com.kroune.nineMensMorrisApp.data.remote.Common.networkScope
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
import javax.inject.Inject

/**
 * Repository for interacting with server games
 * TODO: refactor this
 */
class GameRepository @Inject constructor() : GameRepositoryI {
    override fun startSearchingGame() {
        if (searchingForGameJob?.isCompleted == false) {
            return
        }
        searchingForGameJob = CoroutineScope(networkScope).async {
            runCatching {
                val jwtTokenState = jwtToken
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

    override suspend fun awaitForGameSearchEnd(): Result<Long>? {
        return searchingForGameJob?.await()
    }

    /**
     * job created when searching for a game
     */
    private var searchingForGameJob: Deferred<Result<Long>>? = null

    override val movesQueue = ConcurrentLinkedQueue<Movement>()

    override suspend fun isPlaying(): Result<Long?> {
        return runCatching {
            val jwtTokenState = jwtToken
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
