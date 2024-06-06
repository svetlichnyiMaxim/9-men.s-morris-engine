package com.kr8ne.mensMorris.viewModel.impl.game

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.Position
import com.kr8ne.mensMorris.data.impl.game.GameEndData
import com.kr8ne.mensMorris.ui.impl.game.GameEndScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * game end model
 */
class GameEndViewModel(pos: Position, navController: NavHostController?) : ViewModelI() {
    /**
     * our current game board
     */
    val gameBoard = GameBoardViewModel(pos, navController = navController)
    override var render: ScreenModel = GameEndScreen(gameBoard, navController)
    override val data = GameEndData(gameBoard)
}
