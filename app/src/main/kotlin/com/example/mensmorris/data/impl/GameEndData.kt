package com.example.mensmorris.data.impl

import com.example.mensmorris.common.gameBoard.GameBoard
import com.example.mensmorris.data.DataModel

/**
 * data for game end screen
 */
class GameEndData(
    /**
     * our game board
     */
    var gameBoard: GameBoard
) : DataModel {
    override suspend fun invokeBackend() {
        gameBoard.moveHints.value = arrayListOf()
    }

    override fun clearTheScene() {
        TODO("Not yet implemented")
    }
}
