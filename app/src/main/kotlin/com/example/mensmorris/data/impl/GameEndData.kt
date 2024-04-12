package com.example.mensmorris.data.impl

import androidx.lifecycle.ViewModel
import com.example.mensmorris.common.gameBoard.GameBoard
import com.example.mensmorris.data.DataModel

/**
 * data for game end screen
 */
class GameEndData(
    /**
     * our game board
     */
    var gameBoard: GameBoard, override val viewModel: ViewModel
) : DataModel
