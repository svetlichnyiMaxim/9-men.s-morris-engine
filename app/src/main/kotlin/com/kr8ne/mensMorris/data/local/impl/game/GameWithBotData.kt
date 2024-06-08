package com.kr8ne.mensMorris.data.local.impl.game

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.GameState
import com.kr8ne.mensMorris.cache.Cache
import com.kr8ne.mensMorris.data.local.interfaces.DataI
import com.kr8ne.mensMorris.data.local.interfaces.GameBoardInterface
import com.kr8ne.mensMorris.gameStartPosition
import com.kr8ne.mensMorris.ui.impl.game.GameBoardScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * data for game with bot screen
 */
class GameWithBotData(
    navController: NavHostController?,
    private val viewModelScope: CoroutineScope
) : DataI(), GameBoardInterface {
    /**
     * current game position
     */
    override val gameBoard = GameBoardScreen(
        pos = gameStartPosition,
        onClick = { index -> this.response(index) },
        handleUndo = { onUndo() },
        navController = navController
    )
    private val analyze = GameAnalyzeData(gameBoard.positionStateFlow)

    /**
     * performs needed actions after click
     * @param index index of the clicked element
     */
    private fun GameBoardData.response(index: Int) {
        if (gameBoard.pos.pieceToMove) {
            handleClick(index)
            handleHighLighting()
            botJob = viewModelScope.launch {
                while (!gameBoard.pos.pieceToMove && gameBoard.pos.gameState() != GameState.End) {
                    launchBot()
                }
            }
        }
    }

    private fun onUndo() {
        botJob?.cancel()
        botJob = viewModelScope.launch {
            delay(800)
            while (!gameBoard.pos.pieceToMove && gameBoard.pos.gameState() != GameState.End) {
                launchBot()
            }
        }
    }

    override fun invokeBackend() {
        Cache.resetCacheDepth()
    }

    private var botJob: Job? = null

    /**
     * launches bot actions against player
     */
    private suspend fun launchBot() {
        if (!gameBoard.pos.pieceToMove
            && gameBoard.pos.gameState() != GameState.End
        ) {
            Cache.resetCacheDepth()
            analyze.startAnalyze()
            analyze.analyzeJob?.join()
            gameBoard.viewModel.data.processMove(analyze.result.value.positions.last())
        }
    }
}
