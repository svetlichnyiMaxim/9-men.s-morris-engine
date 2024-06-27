package com.kroune.nineMensMorrisApp.data.remote.account

/**
 * interface for account repository
 * provides information about account
 */
interface AccountInfoRepositoryI {
    /**
     * @return account login (name) by it's id
     */
    fun getAccountDateById(id: Long): Result<Triple<Int, Int, Int>>

    /**
     * @return account login (name) by it's id
     */
    fun getAccountNameById(id: Long): Result<String>

    /**
     * @return account picture by it's id
     */
    suspend fun getAccountPictureById(id: Long): Result<ByteArray>
}
