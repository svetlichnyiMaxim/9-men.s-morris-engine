package com.kroune.mensMorris.data.remote.account

/**
 * interface for account repository
 * provides information about account
 */
interface AccountInfoRepositoryI {
    /**
     * @return account login (name) by it's id
     */
    fun getAccountLoginById(id: Long): String
}
