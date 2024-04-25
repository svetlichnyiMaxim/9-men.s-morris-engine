package com.kr8ne.mensMorris.model.impl

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.data.impl.WelcomeData
import com.kr8ne.mensMorris.domain.impl.WelcomeScreen
import com.kr8ne.mensMorris.domain.interfaces.ScreenModel
import com.kr8ne.mensMorris.model.interfaces.ViewModelI

/**
 * welcome model
 * called when app is launched
 */
class WelcomeViewModel(navController: NavHostController) : ViewModelI() {
    override var render: ScreenModel = WelcomeScreen(navController)
    override val data = WelcomeData()
}
