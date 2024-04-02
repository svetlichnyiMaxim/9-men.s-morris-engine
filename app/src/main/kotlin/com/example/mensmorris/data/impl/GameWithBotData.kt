package com.example.mensmorris.data.impl

import com.example.mensmorris.Position
import com.example.mensmorris.data.DataModel
import com.example.mensmorris.data.GameBoardInterface
import com.example.mensmorris.data.PosInterface
import com.example.mensmorris.gameBoard.GameBoard
import com.example.mensmorris.utils.AnalyzeUtils
import com.example.mensmorris.utils.CacheUtils
import com.example.mensmorris.utils.CoroutineUtils
import com.example.mensmorris.utils.GameUtils
import kotlinx.coroutines.delay

class GameWithBotData(override val gameBoard: GameBoard) : DataModel, GameBoardInterface {
    //,
    //                onClick = { index, func -> response(index, func) }, onUndo = { launchBot() })
    /**
     * performs needed actions after click
     * @param index index of the clicked element
     * @param func function that handles our click
     */
    private fun response(index: Int, func: (index: Int) -> Unit) {
        if (GameUtils.pos.pieceToMove) {
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
        CoroutineUtils.updateBotJob {
            while (!GameUtils.pos.pieceToMove && GameUtils.pos.gameState() != GameUtils.GameState.End) {
                AnalyzeUtils.startAnalyze()
                if (start) {
                    delay(750)
                    start = false
                }
                gameBoard.processMove(CacheUtils.solveResult.value.last())
                CacheUtils.resetCachedPositions()
            }
        }
    }
}