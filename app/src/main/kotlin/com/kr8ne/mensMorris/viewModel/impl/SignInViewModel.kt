package com.kr8ne.mensMorris.viewModel.impl

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.data.impl.SignInData
import com.kr8ne.mensMorris.ui.impl.SignInScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * This class is responsible for managing the data and UI logic for the sign-in screen.
 *
 * @param navController The NavHostController instance that is used to navigate between screens.
 */
class SignInViewModel(navController: NavHostController?) : ViewModelI() {
    override val data: SignInData = SignInData()
    override val render: ScreenModel = SignInScreen(navController,
        { login -> data.loginValidator(login) }, { password -> data.passwordValidator(password) })
}
