package com.example.mensmorris.data

import androidx.lifecycle.MutableLiveData
import com.example.mensmorris.common.gameBoard.GameBoard

/**
 * interface with game board value
 */
interface GameBoardInterface {
    /**
     * our game board
     * usually used for rendering
     */
    val gameBoard: MutableLiveData<GameBoard>
}
