package com.example.mensmorris.data.impl

import androidx.compose.runtime.mutableStateOf
import com.example.mensmorris.common.gameBoard.GameBoard
import com.example.mensmorris.common.utils.CacheUtils
import com.example.mensmorris.common.utils.GameUtils.gameStartPosition
import com.example.mensmorris.data.DataModel
import com.example.mensmorris.data.GameBoardInterface

/**
 * data for game with friend screen
 */
open class GameWithFriendData : DataModel, GameBoardInterface {
    override val gameBoard = GameBoard(pos = mutableStateOf(gameStartPosition),
        onClick = { index, func -> func(index) },
        onUndo = {})


    override suspend fun invokeBackend() {
        CacheUtils.resetCachedPositions()
    }

    override fun clearTheScene() {
        CacheUtils.position = gameBoard.pos.value
    }
}
