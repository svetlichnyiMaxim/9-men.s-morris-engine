package com.example.mensmorris.data.impl

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.example.mensmorris.common.gameBoard.GameBoard
import com.example.mensmorris.common.utils.CacheUtils
import com.example.mensmorris.common.utils.CoroutineUtils
import com.example.mensmorris.common.utils.GameUtils
import com.example.mensmorris.data.DataModel
import com.example.mensmorris.data.GameBoardInterface
import com.example.mensmorris.model.impl.GameAnalyzeViewModel
import kotlinx.coroutines.delay

/**
 * data for game with bot screen
 */
class GameWithBotData : DataModel, GameBoardInterface {
    override val gameBoard by mutableStateOf(
        GameBoard(position = mutableStateOf(GameUtils.gameStartPosition),
            onClick = { index, func -> response(index, func) },
            onUndo = {})
    )
    private val analyze = GameAnalyzeViewModel(gameBoard.position)

    /**
     * performs needed actions after click
     * @param index index of the clicked element
     * @param func function that handles our click
     */
    private fun response(index: Int, func: (index: Int) -> Unit) {
        if (gameBoard.position.value.pieceToMove) {
            func(index)
            launchBot()
        }
    }

    override fun invokeBackend() {
        gameBoard.position.value = GameUtils.gameStartPosition
        CacheUtils.occurredPositions.clear()
    }

    override fun clearTheScene() {
        CoroutineUtils.stopBot()
    }

    /**
     * launches bot actions against player
     */
    private fun launchBot() {
        CoroutineUtils.stopBot()
        var start = true
        val gameboard = gameBoard.position.value
        CoroutineUtils.updateBotJob {
            while (!gameboard.pieceToMove && gameboard.gameState() != GameUtils.GameState.End) {
                analyze.data.startAnalyze()
                if (start) {
                    delay(750)
                    start = false
                }
                gameBoard.processMove(analyze.data.solveResult.value!!.last())
                CacheUtils.resetCachedPositions()
            }
        }
    }
}
