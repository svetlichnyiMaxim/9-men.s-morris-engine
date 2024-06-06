package com.kr8ne.mensMorris.data.local.impl.game

import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.GameState
import com.kr8ne.mensMorris.cache.Cache
import com.kr8ne.mensMorris.data.local.interfaces.DataModel
import com.kr8ne.mensMorris.data.local.interfaces.GameBoardInterface
import com.kr8ne.mensMorris.gameStartPosition
import com.kr8ne.mensMorris.viewModel.impl.game.GameAnalyzeViewModel
import com.kr8ne.mensMorris.viewModel.impl.game.GameBoardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * data for game with bot screen
 */
class GameWithBotData(
    navController: NavHostController?,
    private val viewModelScope: CoroutineScope
) :
    DataModel, GameBoardInterface {
    /**
     * current game position
     */
    val position = mutableStateOf(gameStartPosition)
    override val gameBoard = GameBoardViewModel(
        pos = position,
        onClick = { index -> this.response(index) },
        onUndo = { onUndo() },
        navController = navController
    )
    private val analyze = GameAnalyzeViewModel(gameBoard.data.pos)

    /**
     * performs needed actions after click
     * @param index index of the clicked element
     * @param func function that handles our click
     */
    private fun GameBoardData.response(index: Int) {
        if (position.value.pieceToMove) {
            handleClick(index)
            handleHighLighting()
            botJob = viewModelScope.launch {
                while (!position.value.pieceToMove) {
                    launchBot()
                }
            }
        }
    }

    private fun onUndo() {
        botJob?.cancel()
        botJob = viewModelScope.launch {
            delay(800)
            while (!position.value.pieceToMove && position.value.gameState() != GameState.End) {
                launchBot()
            }
        }
    }

    override suspend fun invokeBackend() {
        Cache.resetCacheDepth()
    }

    private var botJob: Job? = null

    /**
     * launches bot actions against player
     */
    private suspend fun launchBot() {
        if (!position.value.pieceToMove
            && position.value.gameState() != GameState.End
        ) {
            Cache.resetCacheDepth()
            val solveResultValue = analyze.data.getAnalyzeResult()
            analyze.data.solveResult.value = solveResultValue!!
            withContext(Dispatchers.Main) {
                gameBoard.data.processMove(analyze.data.solveResult.value.last())
            }
        }
    }
}
