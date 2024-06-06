package com.kr8ne.mensMorris.viewModel.impl.game

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.data.impl.game.SearchingForGameData
import com.kr8ne.mensMorris.ui.impl.game.SearchingForGameScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * game with bot model
 */
class SearchingForGameViewModel(navController: NavHostController?) : ViewModelI() {
    override val data = SearchingForGameData(navController)
    override var render: ScreenModel = SearchingForGameScreen()
}
