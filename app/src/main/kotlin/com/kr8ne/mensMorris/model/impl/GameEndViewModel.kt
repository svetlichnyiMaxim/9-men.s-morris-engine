package com.kr8ne.mensMorris.model.impl

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.common.gameBoard.GameBoard
import com.kr8ne.mensMorris.common.gameBoard.Position
import com.kr8ne.mensMorris.data.impl.GameEndData
import com.kr8ne.mensMorris.domain.impl.GameEndScreen
import com.kr8ne.mensMorris.domain.interfaces.ScreenModel
import com.kr8ne.mensMorris.model.interfaces.ViewModelI

/**
 * game end model
 */
class GameEndViewModel(pos: Position, navController: NavHostController) : ViewModelI() {
    /**
     * our current game board
     */
    val gameBoard = GameBoard(pos, navController = navController)
    override var render: ScreenModel = GameEndScreen(gameBoard, navController)
    override val data = GameEndData(gameBoard)
}
