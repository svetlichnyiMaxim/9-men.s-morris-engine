package com.kr8ne.mensMorris.viewModel.impl.auth

import com.kr8ne.mensMorris.data.local.impl.auth.SignInData
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * This class is responsible for managing the data and UI logic for the sign-in screen.
 */
class SignInViewModel : ViewModelI() {
    override val data: SignInData = SignInData()

    fun loginValidator(login: String): Boolean {
        return data.loginValidator(login)
    }
    fun passwordValidator(password: String): Boolean {
        return data.passwordValidator(password)
    }
}
