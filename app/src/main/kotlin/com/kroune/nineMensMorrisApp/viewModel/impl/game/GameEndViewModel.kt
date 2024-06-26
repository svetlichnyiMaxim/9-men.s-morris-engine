package com.kroune.nineMensMorrisApp.viewModel.impl.game

import com.kroune.nineMensMorrisApp.data.local.impl.game.GameEndData
import com.kroune.nineMensMorrisApp.ui.impl.game.GameBoardScreen
import com.kroune.nineMensMorrisApp.viewModel.interfaces.ViewModelI

/**
 * game end model
 */
class GameEndViewModel(gameBoard: GameBoardScreen) : ViewModelI() {
    /**
     * our current game board
     */
    override val data = GameEndData(gameBoard)
}
