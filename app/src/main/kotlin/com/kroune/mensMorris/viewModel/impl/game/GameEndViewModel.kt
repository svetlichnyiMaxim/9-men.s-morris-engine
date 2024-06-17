package com.kroune.mensMorris.viewModel.impl.game

import com.kroune.mensMorris.data.local.impl.game.GameEndData
import com.kroune.mensMorris.ui.impl.game.GameBoardScreen
import com.kroune.mensMorris.viewModel.interfaces.ViewModelI

/**
 * game end model
 */
class GameEndViewModel(gameBoard: GameBoardScreen) : ViewModelI() {
    /**
     * our current game board
     */
    override val data = GameEndData(gameBoard)
}
