package com.example.mensmorris.data.impl

import com.example.mensmorris.common.Position
import com.example.mensmorris.common.gameBoard.GameBoard
import com.example.mensmorris.data.DataModel
import com.example.mensmorris.common.utils.CacheUtils

/**
 * data for game end screen
 */
class GameEndData(var gameBoard: GameBoard) : DataModel {
    override fun invokeBackend() {
        gameBoard.moveHints.value = arrayListOf()
    }

    override fun clearTheScene() {
        TODO("Not yet implemented")
    }
}
