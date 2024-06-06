package com.kr8ne.mensMorris.ui.interfaces

import com.kr8ne.mensMorris.viewModel.impl.game.GameBoardViewModel

/**
 * extends game screen model
 * used for screen where board is drown
 */
interface GameScreenModel : ScreenModel {
    /**
     * game board
     * used for drawing main game board
     */
    val gameBoard: GameBoardViewModel
}
