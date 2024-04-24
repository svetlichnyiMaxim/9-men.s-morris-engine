package com.kr8ne.mensMorris.data.interfaces

import com.kr8ne.mensMorris.model.impl.GameBoardViewModel

/**
 * interface with game board value
 */
interface GameBoardInterface {
    /**
     * our game board
     * usually used for rendering
     */
    val gameBoard: GameBoardViewModel
}
