package com.kr8ne.mensMorris.data.impl

import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.common.gameBoard.GameBoard
import com.kr8ne.mensMorris.common.gameBoard.utils.CacheUtils
import com.kr8ne.mensMorris.common.gameBoard.utils.GameState
import com.kr8ne.mensMorris.common.gameBoard.utils.gameStartPosition
import com.kr8ne.mensMorris.data.interfaces.DataModel
import com.kr8ne.mensMorris.data.interfaces.GameBoardInterface
import com.kr8ne.mensMorris.model.impl.GameAnalyzeViewModel
import kotlinx.coroutines.delay

/**
 * data for game with bot screen
 */
class GameWithBotData(navController: NavHostController) :
    DataModel, GameBoardInterface {
    /**
     * current game position
     */
    val position = mutableStateOf(gameStartPosition)
    override val gameBoard = GameBoard(
        pos = position,
        onClick = { index, func -> response(index, func) },
        onUndo = {},
        navController = navController
    )
    private val analyze = GameAnalyzeViewModel(gameBoard.pos)

    /**
     * performs needed actions after click
     * @param index index of the clicked element
     * @param func function that handles our click
     */
    private fun response(index: Int, func: (index: Int) -> Unit) {
        if (position.value.pieceToMove) {
            func(index)
        }
    }

    override suspend fun invokeBackend() {
        CacheUtils.resetCachedPositions()
        launchBot()
    }

    /**
     * launches bot actions against player
     * FIXME: this doesn't work
     * TODO: possibly replace with live data and observer
     */
    private suspend fun launchBot() {
        while (true) {
            delay(500)
            if (!position.value.pieceToMove
                && position.value.gameState() != GameState.End
            ) {
                val solveResultValue = analyze.data.getAnalyzeResult()
                analyze.data.solveResult.value = solveResultValue!!
                gameBoard.processMove(analyze.data.solveResult.value.last())
                CacheUtils.resetCachedPositions()
            }
        }
    }
}
