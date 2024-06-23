package com.kroune.mensMorris.viewModel.impl.auth

import com.kroune.mensMorris.data.local.impl.auth.SignUpData
import com.kroune.mensMorris.data.remote.auth.AuthRepositoryI
import com.kroune.mensMorris.viewModel.interfaces.ViewModelI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * This class is responsible for managing the data and UI rendering for the sign-up screen
 */
@HiltViewModel
class SignUpViewModel @Inject constructor(private val authRepositoryI: AuthRepositoryI) : ViewModelI() {
    override val data: SignUpData = SignUpData()

    /**
     * registers new account
     */
    suspend fun register(login: String, password: String): Result<String> {
        return authRepositoryI.register(login, password)
    }

    /**
     * quick access
     */
    fun loginValidator(login: String): Boolean {
        return authRepositoryI.loginValidator(login)
    }

    /**
     * quick access
     */
    fun passwordValidator(password: String): Boolean {
        return authRepositoryI.passwordValidator(password)
    }
}
