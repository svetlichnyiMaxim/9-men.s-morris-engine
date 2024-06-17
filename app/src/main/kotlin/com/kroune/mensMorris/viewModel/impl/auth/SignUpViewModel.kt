package com.kroune.mensMorris.viewModel.impl.auth

import com.kroune.mensMorris.data.local.impl.auth.SignUpData
import com.kroune.mensMorris.viewModel.interfaces.ViewModelI

/**
 * This class is responsible for managing the data and UI rendering for the sign-up screen
 */
class SignUpViewModel : ViewModelI() {
    override val data: SignUpData = SignUpData()

    /**
     * quick access
     */
    fun loginValidator(login: String): Boolean {
        return data.loginValidator(login)
    }

    /**
     * quick access
     */
    fun passwordValidator(password: String): Boolean {
        return data.passwordValidator(password)
    }
}
