package com.kroune.mensMorris.viewModel.impl.auth

import com.kroune.mensMorris.data.local.impl.auth.SignInData
import com.kroune.mensMorris.data.remote.auth.AuthRepositoryI
import com.kroune.mensMorris.viewModel.interfaces.ViewModelI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * This class is responsible for managing the data and UI logic for the sign-in screen.
 */
@HiltViewModel
class SignInViewModel @Inject constructor(private val authRepository: AuthRepositoryI) :
    ViewModelI() {
    override val data: SignInData = SignInData()

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
}
