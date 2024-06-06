package com.kr8ne.mensMorris.data.remote

import androidx.core.content.edit
import com.kr8ne.mensMorris.activity
import com.kr8ne.mensMorris.common.SERVER_ADDRESS
import com.kr8ne.mensMorris.common.USER_API
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.utils.io.printStack

object Auth {
    /**
     * Jwt token provided by the server
     */
    @Volatile
    var jwtToken: String? = activity?.sharedPreferences?.getString("jwtToken", null)
        set(value) {
            field = value
            activity?.sharedPreferences?.edit(commit = true) {
                putString("jwtToken", value).apply()
            }
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
     * Attempts to register the user with the provided credentials.
     *
     * @return [ServerResponse] indicating the success or failure of the registration attempt.
     */
    suspend fun register(login: String, password: String): Result<String> {
        return runCatching {
            val registerResult =
                network.get("http${SERVER_ADDRESS}${USER_API}/reg") {
                    method = HttpMethod.Get
                    url {
                        parameters["login"] = login
                        parameters["password"] = password
                    }
                }
            return when (registerResult.status.value) {
                200 -> {
                    Result.success(registerResult.bodyAsText())
                }

                401 -> {
                    Result.failure(ServerResponse.WrongPasswordOrLogin())
                }

                404 -> {
                    Result.failure(ServerResponse.Unreachable())
                }

                409 -> {
                    Result.failure(ServerResponse.LoginInUse())
                }

                else -> {
                    Result.failure(ServerResponse.UnknownServerError())
                }
            }
        }.onFailure {
            println("error accessing ${"http${SERVER_ADDRESS}${USER_API}/reg"}")
            it.printStack()
        }
    }

    /**
     * Attempts to login the user with the provided credentials.
     *
     * @return [ServerResponse] indicating the success or failure of the login attempt.
     */
    suspend fun login(login: String, password: String): Result<String> {
        return runCatching {
            val loginResult =
                network.get("http${SERVER_ADDRESS}${USER_API}/login") {
                    method = HttpMethod.Get
                    url {
                        parameters["login"] = login
                        parameters["password"] = password
                    }
                }
            return when (loginResult.status.value) {
                200 -> {
                    Result.success(loginResult.bodyAsText())
                }

                401 -> {
                    Result.failure(ServerResponse.WrongPasswordOrLogin())
                }

                404 -> {
                    Result.failure(ServerResponse.Unreachable())
                }

                else -> {
                    Result.failure(ServerResponse.UnknownServerError())
                }
            }
        }.onFailure {
            println("error accessing ${"http${SERVER_ADDRESS}${USER_API}/login"}")
            it.printStack()
        }
    }

    suspend fun checkJwtToken(): Result<Boolean> {
        return runCatching {
            val jwtTokenState = jwtToken
            require(jwtTokenState != null)
            val result = network.get("http$SERVER_ADDRESS$USER_API/check-jwt-token") {
                method = HttpMethod.Get
                url {
                    parameters["jwtToken"] = jwtTokenState
                }
            }
            result.bodyAsText().toBoolean()
        }
    }
}

/**
 * Represents the server's response to client requests.
 */
sealed class ServerResponse : Exception() {
    /**
     * Represents a server response indicating that the login is already in use.
     */
    class LoginInUse : ServerResponse()

    /**
     * Represents a server response indicating that the provided password or login is incorrect.
     */
    class WrongPasswordOrLogin : ServerResponse()

    /**
     * this means that server is currently unreachable
     */
    class Unreachable : ServerResponse()

    /**
     * Represents a server response indicating an unknown error.
     */
    class UnknownServerError : ServerResponse()
}
