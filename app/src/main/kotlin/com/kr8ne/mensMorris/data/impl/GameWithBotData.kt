package com.kr8ne.mensMorris.data.impl

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.common.gameBoard.GameBoard
import com.kr8ne.mensMorris.common.gameBoard.utils.CacheUtils
import com.kr8ne.mensMorris.common.gameBoard.utils.GameState
import com.kr8ne.mensMorris.common.gameBoard.utils.gameStartPosition
import com.kr8ne.mensMorris.data.DataModel
import com.kr8ne.mensMorris.data.GameBoardInterface
import com.kr8ne.mensMorris.model.impl.GameAnalyzeViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * data for game with bot screen
 */
class GameWithBotData(override val viewModel: ViewModel, navController: NavHostController) :
    DataModel, GameBoardInterface {
    override val gameBoard = GameBoard(
        pos = mutableStateOf(gameStartPosition),
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
        if (gameBoard.pos.value.pieceToMove) {
            func(index)
        }
    }

    override suspend fun invokeBackend() {
        CacheUtils.resetCachedPositions()
        launchBot()
    }

    /**
     * launches bot actions against player
     */
    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun launchBot() {
        viewModel.viewModelScope.launch {
            while (true) {
                if (!gameBoard.pos.value.pieceToMove
                    && gameBoard.pos.value.gameState() != GameState.End
                ) {
                    val solveResultValue = analyze.data.getAnalyzeResult()
                    analyze.data.solveResult.value = solveResultValue!!
                    gameBoard.processMove(analyze.data.solveResult.value.last())
                    CacheUtils.resetCachedPositions()
                }
                delay(500)
            }
        }
    }
}
