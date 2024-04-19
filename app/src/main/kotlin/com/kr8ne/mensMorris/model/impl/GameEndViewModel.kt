package com.kr8ne.mensMorris.model.impl

import com.kr8ne.mensMorris.common.gameBoard.GameBoard
import com.kr8ne.mensMorris.common.gameBoard.Position
import com.kr8ne.mensMorris.data.impl.GameEndData
import com.kr8ne.mensMorris.domain.ScreenModel
import com.kr8ne.mensMorris.domain.impl.GameEndScreen
import com.kr8ne.mensMorris.model.ViewModelI

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
