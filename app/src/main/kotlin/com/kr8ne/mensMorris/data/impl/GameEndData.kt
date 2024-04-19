package com.kr8ne.mensMorris.data.impl

import androidx.lifecycle.ViewModel
import com.kr8ne.mensMorris.common.gameBoard.GameBoard
import com.kr8ne.mensMorris.data.DataModel

/**
 * data for game end screen
 */
class GameEndData(
    /**
     * our game board
     */
    var gameBoard: GameBoard, override val viewModel: ViewModel
) : DataModel
