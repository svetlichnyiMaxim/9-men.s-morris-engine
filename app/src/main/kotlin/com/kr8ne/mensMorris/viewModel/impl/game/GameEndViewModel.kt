package com.kr8ne.mensMorris.viewModel.impl.game

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.Position
import com.kr8ne.mensMorris.data.local.impl.game.GameEndData
import com.kr8ne.mensMorris.ui.impl.game.GameBoardScreen
import com.kr8ne.mensMorris.ui.impl.game.GameEndScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI
import kotlinx.coroutines.flow.StateFlow

/**
 * game end model
 */
class GameEndViewModel(gameBoard: GameBoardScreen, navController: NavHostController?) : ViewModelI() {
    /**
     * our current game board
     */
    override val data = GameEndData(gameBoard)
}
