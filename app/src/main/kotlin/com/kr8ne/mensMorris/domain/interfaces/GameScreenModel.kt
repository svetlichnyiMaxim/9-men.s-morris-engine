package com.kr8ne.mensMorris.domain.interfaces

import com.kr8ne.mensMorris.model.impl.GameBoardViewModel

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
