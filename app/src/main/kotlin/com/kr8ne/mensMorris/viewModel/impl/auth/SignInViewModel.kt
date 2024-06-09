package com.kr8ne.mensMorris.viewModel.impl.auth

import com.kr8ne.mensMorris.data.local.impl.auth.SignInData
import com.kr8ne.mensMorris.data.remote.AuthRepository
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * This class is responsible for managing the data and UI logic for the sign-in screen.
 */
class SignInViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModelI() {
    override val data: SignInData = SignInData()

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
