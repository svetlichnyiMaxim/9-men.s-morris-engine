package com.kr8ne.mensMorris.viewModel.impl

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.data.impl.SearchingForGameData
import com.kr8ne.mensMorris.ui.impl.SearchingForGameScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * game with bot model
 */
class SearchingForGameViewModel(navController: NavHostController) : ViewModelI() {
    override val data = SearchingForGameData(navController)
    override var render: ScreenModel = SearchingForGameScreen()
}
