package com.kr8ne.mensMorris.viewModel.impl

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.data.local.impl.WelcomeData
import com.kr8ne.mensMorris.ui.impl.WelcomeScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * welcome model
 * called when app is launched
 */
class WelcomeViewModel(
) : ViewModelI() {
    override val data = WelcomeData()
}
