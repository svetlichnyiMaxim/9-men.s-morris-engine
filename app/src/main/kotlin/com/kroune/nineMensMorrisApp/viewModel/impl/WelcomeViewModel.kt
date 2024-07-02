package com.kroune.nineMensMorrisApp.viewModel.impl

import com.kroune.nineMensMorrisApp.data.remote.account.AccountInfoRepositoryI
import com.kroune.nineMensMorrisApp.data.remote.auth.AuthRepositoryI
import com.kroune.nineMensMorrisApp.viewModel.interfaces.ViewModelI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * welcome model
 * called when app is launched
 */
@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val authRepositoryI: AuthRepositoryI,
    private val accountInfoRepositoryI: AccountInfoRepositoryI
) : ViewModelI() {

    /**
     * state flow of the account id
     */
    val accountId: MutableStateFlow<Long?>
        get() {
            return accountInfoRepositoryI.accountIdState
        }

    /**
     * tells if jwt token exists
     */
    fun hasJwtToken(): Boolean {
        return accountInfoRepositoryI.jwtTokenState.value != null
    }

    /**
     * checks if jwt token is valid
     */
    suspend fun checkJwtToken(): Result<Boolean> {
        val jwtToken = accountInfoRepositoryI.jwtTokenState.value ?: return Result.failure(
            IllegalArgumentException("no jwt token was provided")
        )
        return authRepositoryI.checkJwtToken(jwtToken)
    }
}
