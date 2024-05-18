package com.kr8ne.mensMorris.api

import com.kr8ne.mensMorris.common.game.Movement
import com.kr8ne.mensMorris.data.interfaces.GameBoardInterface
import com.kr8ne.mensMorris.plus
import com.kr8ne.mensMorris.ui.interfaces.GameScreenModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.utils.io.printStack
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.LinkedList
import java.util.Queue

/**
 * Object representing the client for interacting with the server.
 */
object Client {
    private var userData: UserData = UserData("", "")

    /**
     * Jwt token provided by the server
     */
    var jwtToken: String? = null

    /**
     * The server's address.
     * put your network ip here
     */
    private const val SERVER_ADDRESS = "://10.68.154.156:8080"

    /**
     * The API endpoint for user-related operations.
     */
    private const val USER_API = "/api/v1/user"

    /**
     * The network scope for asynchronous operations.
     */
    val networkScope = Dispatchers.IO

    val movesQueue: Queue<Movement> = LinkedList()

    /**
     * The network client for making HTTP requests.
     */
    private val network = HttpClient(OkHttp) {
        install(WebSockets)
    }

    /**
     * Validates the provided login.
     *
     * @param login The login to be validated.
     * @return True if the login is valid, false otherwise.
     */
    fun loginValidator(login: String): Boolean {
        return login.length >= 6
    }

    /**
     * Validates the provided password.
     *
     * @param password The password to be validated.
     * @return True if the password is valid, false otherwise.
     */
    fun passwordValidator(password: String): Boolean {
        return password.length >= 6
    }

    /**
     * Updates the user's login.
     *
     * @param newLogin The new value of login.
     */
    fun updateUserLogin(newLogin: String) {
        userData.login = newLogin
    }

    /**
     * Updates the user's password.
     *
     * @param newLogin The new value of password.
     */
    fun updateUserPassword(newLogin: String) {
        userData.password = newLogin
    }


    /**
     * Attempts to register the user with the provided credentials.
     *
     * @return [ServerResponse] indicating the success or failure of the registration attempt.
     */
    suspend fun register(): Result<ServerResponse> {
        val userDataState: UserData = userData
        return runCatching {
            val registerResult = network.get("http$SERVER_ADDRESS${USER_API}/reg") {
                method = HttpMethod.Get
                url {
                    parameters["login"] = userDataState.login
                    parameters["password"] = userDataState.password
                }
            }
            when (registerResult.status.value) {
                200 -> {
                    jwtToken = registerResult.bodyAsText()
                    return Result.success(ServerResponse.Success(jwtToken!!))
                }

                401 -> {
                    return Result.success(ServerResponse.WrongPasswordOrLogin)
                }

                409 -> {
                    return Result.success(ServerResponse.LoginIsInUse)
                }

                else -> {
                    return Result.success(ServerResponse.ServerError)
                }
            }
        }.onFailure {
            println("error accessing ${"http$SERVER_ADDRESS$USER_API/reg"}")
            it.printStack()
        }
    }

    /**
     * Attempts to login the user with the provided credentials.
     *
     * @return [ServerResponse] indicating the success or failure of the login attempt.
     */
    suspend fun login(): Result<ServerResponse> {
        val userDataState: UserData = userData
        return runCatching {
            val registerResult = network.get("http$SERVER_ADDRESS${USER_API}/login") {
                method = HttpMethod.Get
                url {
                    parameters["login"] = userDataState.login
                    parameters["password"] = userDataState.password
                }
            }
            when (registerResult.status.value) {
                200 -> {
                    jwtToken = registerResult.bodyAsText()
                    return Result.success(ServerResponse.Success(jwtToken!!))
                }

                401 -> {
                    return Result.success(ServerResponse.WrongPasswordOrLogin)
                }

                else -> {
                    return Result.success(ServerResponse.ServerError)
                }
            }
        }.onFailure {
            println("error accessing ${"http$SERVER_ADDRESS$USER_API/login"}")
            it.printStack()
        }
    }

    /**
     * Starts searching for a game.
     *
     * @return [ServerResponse] indicating the success or failure of the search attempt.
     */
    suspend fun startSearchingGame(): Result<String> {
        //TODO: finish this
        return runCatching {
            require(jwtToken != null)
            var gameId: String? = null
            network.webSocket("ws$SERVER_ADDRESS$USER_API/search-for-game") {
                send(jwtToken!!)
                while (true) {
                    val serverMessage = (incoming.receive() as? Frame.Text)?.readText() ?: continue
                    println("game id: $serverMessage")
                    gameId = serverMessage
                    close(CloseReason(CloseReason.Codes.NORMAL, "ok"))
                    break
                }
            }
            return@runCatching gameId!!
        }.onFailure {
            println("error accessing ${"ws$SERVER_ADDRESS$USER_API/search-for-game"}")
            it.printStack()
        }
    }

    suspend fun playGame(gameId: String, classParent: GameBoardInterface) {
        runCatching {
            network.webSocket("ws$SERVER_ADDRESS$USER_API/game-$gameId") {
                while (true) {
                    while (movesQueue.isNotEmpty()) {
                        val string = Json.encodeToString<MovementAdapter>(
                            MovementAdapter(
                                movesQueue.peek()!!.startIndex,
                                movesQueue.peek()!!.endIndex
                            )
                        )
                        // post our move
                        send(string)
                        movesQueue.remove()
                    }
                    // receive the server's data
                    val serverMessage = incoming.receive() as? Frame.Text ?: continue
                    val position = Json.decodeFromString<PositionAdapter>(serverMessage.readText())
                    classParent.gameBoard.data.pos.value.let {
                        it.positions = position.positions
                        it.freePieces = position.freePieces
                        it.pieceToMove = position.pieceToMove
                        it.removalCount = position.removalCount
                        it.greenPiecesAmount =
                            (it.positions.count { it1 -> it1 == true } + it.freePieces.first)
                        it.bluePiecesAmount =
                            (it.positions.count { it1 -> it1 == false } + it.freePieces.second)
                    }
                }
            }
        }.onFailure() {
            println("error accessing ${"$SERVER_ADDRESS$USER_API/game-$gameId"}")
            it.printStack()
        }
    }
}

@Serializable
class PositionAdapter(
    @Serializable val positions: Array<Boolean?>,
    @Serializable val freePieces: Pair<UByte, UByte> = Pair(0U, 0U),
    @Serializable val pieceToMove: Boolean,
    @Serializable val removalCount: Byte = 0
)

@Serializable
class MovementAdapter(
    @Serializable val startIndex: Int?, @Serializable val endIndex: Int?
)

/**
 * Represents the server's response to client requests.
 */
sealed class ServerResponse {
    /**
     * Represents a successful server response.
     *
     * @param jwtToken The JWT token returned by the server.
     */
    data class Success(val jwtToken: String) : ServerResponse()

    /**
     * Represents a server response indicating that the login is already in use.
     */
    data object LoginIsInUse : ServerResponse()

    /**
     * Represents a server response indicating that the provided password or login is incorrect.
     */
    data object WrongPasswordOrLogin : ServerResponse()

    /**
     * Represents a server response indicating an unknown error.
     */
    data object ServerError : ServerResponse()
}

/**
 * Class representing the user's data.
 */
class UserData(
    /**
     * The user's login.
     */
    var login: String,

    /**
     * The user's password.
     */
    var password: String
)
