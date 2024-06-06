package com.kr8ne.mensMorris.viewModel.impl.game

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.data.impl.game.GameWithBotData
import com.kr8ne.mensMorris.ui.impl.game.GameWithBotScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * game with bot model
 */
class GameWithBotViewModel(navController: NavHostController?) : ViewModelI() {
    override val data = GameWithBotData(navController, viewModelScope)
    override var render: ScreenModel = GameWithBotScreen(data.gameBoard)
}
