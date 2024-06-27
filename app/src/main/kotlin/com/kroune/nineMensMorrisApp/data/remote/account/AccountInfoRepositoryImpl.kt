package com.kroune.nineMensMorrisApp.data.remote.account

import com.kroune.nineMensMorrisApp.data.remote.Common.network
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

/**
 * remote repository implementation
 */
class AccountInfoRepositoryImpl : AccountInfoRepositoryI {
    // TODO: implement this on server

    override fun getAccountDateById(id: Long): Result<Triple<Int, Int, Int>> {
        return Result.success(Triple(1, 2, 3456))
    }

    override fun getAccountNameById(id: Long): Result<String> {
        return runCatching {
            "idk, not implemented yet"
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
