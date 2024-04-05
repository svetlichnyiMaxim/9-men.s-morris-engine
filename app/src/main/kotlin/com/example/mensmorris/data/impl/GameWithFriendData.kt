package com.example.mensmorris.data.impl

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import com.example.mensmorris.common.gameBoard.GameBoard
import com.example.mensmorris.common.utils.CacheUtils
import com.example.mensmorris.common.utils.GameUtils.gameStartPosition
import com.example.mensmorris.data.DataModel
import com.example.mensmorris.data.GameBoardInterface

/**
 * data for game with friend screen
 */
open class GameWithFriendData : DataModel, GameBoardInterface {
    override val gameBoard = MutableLiveData(
        GameBoard(pos = mutableStateOf(gameStartPosition),
            onClick = { index, func -> func(index) },
            onUndo = {})
    )

    override suspend fun invokeBackend() {
        CacheUtils.resetCachedPositions()
    }

    override fun clearTheScene() {
        CacheUtils.position = gameBoard.value!!.pos.value
    }
}
