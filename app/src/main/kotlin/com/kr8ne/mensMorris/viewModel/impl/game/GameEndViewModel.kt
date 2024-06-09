package com.kr8ne.mensMorris.viewModel.impl.game

import com.kr8ne.mensMorris.data.local.impl.game.GameEndData
import com.kr8ne.mensMorris.ui.impl.game.GameBoardScreen
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * game end model
 */
class GameEndViewModel(gameBoard: GameBoardScreen) : ViewModelI() {
    /**
     * our current game board
     */
    override val data = GameEndData(gameBoard)
}
