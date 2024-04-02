package com.example.mensmorris.domain

import com.example.mensmorris.gameBoard.GameBoard

/**
 * extends game screen model
 * used for screen where board is drown
 */
interface GameScreenModel : ScreenModel {
    /**
     * game board
     * used for drawing main game board
     */
    val gameBoard: GameBoard
}
