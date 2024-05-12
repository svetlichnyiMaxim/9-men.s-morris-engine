package com.kr8ne.mensMorris.viewModel.impl

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.data.impl.SignUpData
import com.kr8ne.mensMorris.ui.impl.SignUpScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * This class is responsible for managing the data and UI rendering for the sign-up screen
 *
 * @param navController The [NavHostController] instance to navigate between screens.
 */
class SignUpViewModel(navController: NavHostController) : ViewModelI() {
    override val data: SignUpData = SignUpData()
    override val render: ScreenModel = SignUpScreen(navController,
        { login -> data.loginValidator(login) }, { password -> data.passwordValidator(password) })
}
