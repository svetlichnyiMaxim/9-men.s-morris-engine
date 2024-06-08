package com.kr8ne.mensMorris.viewModel.impl

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.data.local.impl.AppStartAnimationData
import com.kr8ne.mensMorris.ui.impl.AppStartAnimationScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * ViewModel for the loading animation screen.
 */
class AppStartAnimationViewModel(): ViewModelI() {
    override val data: AppStartAnimationData = AppStartAnimationData()
}
