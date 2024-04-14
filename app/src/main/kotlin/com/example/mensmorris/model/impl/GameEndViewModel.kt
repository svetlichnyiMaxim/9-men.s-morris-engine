package com.example.mensmorris.model.impl

import com.example.mensmorris.common.gameBoard.GameBoard
import com.example.mensmorris.common.gameBoard.Position
import com.example.mensmorris.data.impl.GameEndData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.GameEndScreen
import com.example.mensmorris.model.ViewModelI

/**
 * game end model
 */
class GameEndViewModel(pos: Position) : ViewModelI() {
    /**
     * our current game board
     */
    val gameBoard = GameBoard(pos)
    override var render: ScreenModel = GameEndScreen(gameBoard)
    override val data = GameEndData(gameBoard, this)
}
