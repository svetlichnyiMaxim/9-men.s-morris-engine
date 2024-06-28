package com.kroune.nineMensMorrisApp.data.remote.account

import com.kroune.nineMensMorrisApp.common.SERVER_ADDRESS
import com.kroune.nineMensMorrisApp.common.USER_API
import com.kroune.nineMensMorrisApp.data.remote.Common.network
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import kotlinx.serialization.json.Json

/**
 * remote repository implementation
 */
class AccountInfoRepositoryImpl : AccountInfoRepositoryI {
    // TODO: implement this on server

    override suspend fun getAccountDateById(id: Long): Result<Triple<Int, Int, Int>?> {
        return runCatching {
            val request = network.get("http${SERVER_ADDRESS}${USER_API}/get-creation-date-by-id") {
                method = HttpMethod.Get
                url {
                    parameters["id"] = id.toString()
                }
            }.bodyAsText()
            Json.decodeFromString<Triple<Int, Int, Int>?>(request)
        }.onFailure {
            println("error getting account creation date $id")
            it.printStackTrace()
        }
    }

    override suspend fun getAccountNameById(id: Long): Result<String?> {
        return runCatching {
            val request = network.get("http${SERVER_ADDRESS}${USER_API}/get-login-by-id") {
                method = HttpMethod.Get
                url {
                    parameters["id"] = id.toString()
                }
            }.bodyAsText()
            Json.decodeFromString<String?>(request)
        }.onFailure {
            println("error getting account name $id")
            it.printStackTrace()
        }
    }

    override suspend fun getAccountPictureById(id: Long): Result<ByteArray> {
        return runCatching<AccountInfoRepositoryImpl, ByteArray> {
            val url = "https://shapka-youtube.ru/wp-content/uploads/2020/08/man-silhouette.jpg"
            val httpResponse: HttpResponse = network.get(url)
            httpResponse.body()
        }.onFailure {
            println("error getting account picture $id")
            it.printStackTrace()
        }
    }
}
