package com.kr8ne.mensMorris.viewModel.impl

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.data.impl.LoadingAnimationData
import com.kr8ne.mensMorris.ui.impl.LoadingAnimationScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * ViewModel for the loading animation screen.
 */
class LoadingAnimationViewModel(navController: NavHostController): ViewModelI() {
    override val data: LoadingAnimationData = LoadingAnimationData()
    override val render: ScreenModel = LoadingAnimationScreen(navController)
}
