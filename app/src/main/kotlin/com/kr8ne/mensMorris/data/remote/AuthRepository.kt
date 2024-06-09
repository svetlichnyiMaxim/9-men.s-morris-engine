package com.kr8ne.mensMorris.data.remote

import com.kr8ne.mensMorris.common.SERVER_ADDRESS
import com.kr8ne.mensMorris.common.USER_API
import com.kroune.NetworkResponse
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.utils.io.printStack
import kotlinx.serialization.json.Json

/**
 * contains ways to authenticate
 */
class AuthRepository {
    /**
     * Validates the provided login.
     *
     * @param login The login to be validated.
     * @return True if the login is valid, false otherwise.
     */
    fun loginValidator(login: String): Boolean {
        val length = login.length in 5..12
        val content = login.all { it.isLetterOrDigit() }
        return length && content
    }

    /**
     * Validates the provided password.
     *
     * @param password The password to be validated.
     * @return True if the password is valid, false otherwise.
     */
    fun passwordValidator(password: String): Boolean {
        val length = password.length in 7..14
        val validString = password.all { it.isLetterOrDigit() }
        val anyDigits = password.any { it.isDigit() }
        val anyLetters = password.any { it.isLetter() }
        return length && validString && anyDigits && anyLetters
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
                }.bodyAsText()
            val registerResultText = Json.decodeFromString<NetworkResponse>(registerResult)
            return when (registerResultText.code) {
                // TODO: finish this
                200 -> {
                    Result.success(registerResultText.message!!)
                }

                else -> {
                    Result.failure(ServerException(registerResultText.message!!))
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
                }.bodyAsText()
            val loginResultText = Json.decodeFromString<NetworkResponse>(loginResult)
            return when (loginResultText.code) {
                // TODO: finish this
                200 -> {
                    Result.success(loginResultText.message!!)
                }

                else -> {
                    Result.failure(ServerException(loginResultText.message!!))
                }
            }
        }.onFailure {
            println("error accessing ${"http${SERVER_ADDRESS}${USER_API}/login"}")
            it.printStack()
        }
    }

    /**
     * checks if jwt token is valid
     */
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
class ServerException(
    /**
     * server exception description
     */
    val text: String
) : Exception()
