package com.example.mensmorris.data.impl

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mensmorris.common.gameBoard.GameBoard
import com.example.mensmorris.common.utils.CacheUtils
import com.example.mensmorris.common.utils.GameUtils
import com.example.mensmorris.common.utils.GameUtils.gameStartPosition
import com.example.mensmorris.data.DataModel
import com.example.mensmorris.data.GameBoardInterface

/**
 * data for game with friend screen
 */
class GameWithFriendData(override val viewModel: ViewModel) : DataModel, GameBoardInterface {
    override val gameBoard = GameBoard(pos = mutableStateOf(gameStartPosition),
        onClick = { index, func -> func(index) },
        onUndo = {})


    override suspend fun invokeBackend() {
        CacheUtils.resetCachedPositions()
    }

    override fun clearTheScene() {
        CacheUtils.position = gameBoard.pos.value
        gameBoard.pos.value = gameStartPosition
    }
}
