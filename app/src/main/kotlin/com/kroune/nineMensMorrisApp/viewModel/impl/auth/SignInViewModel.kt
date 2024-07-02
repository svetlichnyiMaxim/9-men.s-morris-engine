package com.kroune.nineMensMorrisApp.viewModel.impl.auth

import com.kroune.nineMensMorrisApp.data.remote.account.AccountInfoRepositoryI
import com.kroune.nineMensMorrisApp.data.remote.auth.AuthRepositoryI
import com.kroune.nineMensMorrisApp.viewModel.interfaces.ViewModelI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * This class is responsible for managing the data and UI logic for the sign-in screen.
 */
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepositoryI,
    private val accountInfoRepository: AccountInfoRepositoryI
) : ViewModelI() {

    /**
     * logins into the account
     */
    suspend fun login(login: String, password: String): Result<String> {
        return authRepository.login(login, password)
    }

    /**
     * login validation function
     */
    fun loginValidator(login: String): Boolean {
        return authRepository.loginValidator(login)
    }

    /**
     * password validation function
     */
    fun passwordValidator(password: String): Boolean {
        return authRepository.passwordValidator(password)
    }

    /**
     * updates jwt token
     */
    fun updateJwtToken(token: String) {
        return accountInfoRepository.updateJwtTokenState(token)
    }

    /**
     * gets account id by jwt token
     */
    suspend fun getIdByJwtToken(jwtToken: String): Result<Long?> {
        // this is an easier way to tell compiler that id is always initialized at the end
        var id: Result<Long?> = accountInfoRepository.getIdByJwtToken(jwtToken)
        repeat(5) {
            id = accountInfoRepository.getIdByJwtToken(jwtToken)
            if (id.isSuccess) {
                return id
            }
        }
        return id
    }
}
