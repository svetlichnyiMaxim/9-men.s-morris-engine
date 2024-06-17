package com.kroune.mensMorris.ui.interfaces

import com.kroune.mensMorris.ui.impl.game.GameBoardScreen

/**
 * extends game screen model
 * used for screen where board is drown
 */
interface GameScreenModel : ScreenModel {
    /**
     * game board
     * used for drawing main game board
     */
    val gameBoard: GameBoardScreen
}
