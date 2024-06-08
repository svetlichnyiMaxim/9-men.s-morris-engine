package com.kr8ne.mensMorris.viewModel.impl.game

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.data.local.impl.game.GameWithBotData
import com.kr8ne.mensMorris.ui.impl.game.GameBoardScreen
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * game with bot model
 */
class GameWithBotViewModel(navController: NavHostController?) : ViewModelI() {
    override val data = GameWithBotData(navController, viewModelScope)

    val gameBoard
        get() = data.gameBoard
}
