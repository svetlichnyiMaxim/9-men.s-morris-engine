package com.kr8ne.mensMorris.viewModel.impl.auth

import android.content.res.Resources
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.data.local.impl.auth.SignInData
import com.kr8ne.mensMorris.ui.impl.auth.SignInScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * This class is responsible for managing the data and UI logic for the sign-in screen.
 *
 * @param navController The NavHostController instance that is used to navigate between screens.
 */
class SignInViewModel(navController: NavHostController?, resources: Resources) : ViewModelI() {
    override val data: SignInData = SignInData()

    fun loginValidator(login: String): Boolean {
        return data.loginValidator(login)
    }
    fun passwordValidator(password: String): Boolean {
        return data.passwordValidator(password)
    }
}
