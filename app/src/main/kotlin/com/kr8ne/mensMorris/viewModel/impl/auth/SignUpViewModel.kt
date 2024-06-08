package com.kr8ne.mensMorris.viewModel.impl.auth

import android.content.res.Resources
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.data.local.impl.auth.SignUpData
import com.kr8ne.mensMorris.ui.impl.auth.SignUpScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * This class is responsible for managing the data and UI rendering for the sign-up screen
 *
 * @param navController The [NavHostController] instance to navigate between screens.
 */
class SignUpViewModel : ViewModelI() {
    override val data: SignUpData = SignUpData()
    fun loginValidator(login: String): Boolean {
        return data.loginValidator(login)
    }
    fun passwordValidator(password: String): Boolean {
        return data.passwordValidator(password)
    }
}
