package com.kr8ne.mensMorris.viewModel.impl

import android.content.SharedPreferences
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.data.local.impl.WelcomeData
import com.kr8ne.mensMorris.ui.impl.WelcomeScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * welcome model
 * called when app is launched
 */
class WelcomeViewModel(navController: NavHostController?, sharedPreferences: SharedPreferences) : ViewModelI() {
    override var render: ScreenModel = WelcomeScreen(navController, sharedPreferences)
    override val data = WelcomeData()
}
