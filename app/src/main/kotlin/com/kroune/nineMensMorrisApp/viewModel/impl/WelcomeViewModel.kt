package com.kroune.nineMensMorrisApp.viewModel.impl

import com.kroune.nineMensMorrisApp.data.remote.Common.jwtToken
import com.kroune.nineMensMorrisApp.data.remote.Common.networkScope
import com.kroune.nineMensMorrisApp.data.remote.account.AccountInfoRepositoryI
import com.kroune.nineMensMorrisApp.data.remote.auth.AuthRepositoryI
import com.kroune.nineMensMorrisApp.viewModel.interfaces.ViewModelI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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
     * null = still loading
     * -1 = no valid account
     * else = account id
     */
    val accountId = MutableStateFlow<Long?>(null)

    /**
     * checks if jwt token is valid
     */
    suspend fun checkJwtToken(): Result<Boolean> {
        return authRepositoryI.checkJwtToken()
    }

    init {
        // we check if our jwt token is valid
        CoroutineScope(networkScope).launch {
            val jwtTokenState = jwtToken
            if (jwtTokenState == null) {
                accountId.value = -1L
                return@launch
            }
            val validToken = checkJwtToken().getOrDefault(false)
            if (!validToken) {
                accountId.value = -1L
                return@launch
            }
            val id = accountInfoRepositoryI.getIdByJwtToken(jwtTokenState).getOrNull()
            accountId.value = id
        }
    }
}
