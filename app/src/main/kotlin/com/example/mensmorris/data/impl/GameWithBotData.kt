package com.example.mensmorris.data.impl

import androidx.compose.runtime.mutableStateOf
import com.example.mensmorris.common.gameBoard.GameBoard
import com.example.mensmorris.common.utils.CacheUtils
import com.example.mensmorris.common.utils.GameUtils
import com.example.mensmorris.common.utils.botScope
import com.example.mensmorris.data.DataModel
import com.example.mensmorris.data.GameBoardInterface
import com.example.mensmorris.model.impl.GameAnalyzeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * data for game with bot screen
 */
class GameWithBotData : DataModel, GameBoardInterface {
    override val gameBoard = GameBoard(pos = mutableStateOf(GameUtils.gameStartPosition),
        onClick = { index, func -> response(index, func) },
        onUndo = {}
    )
    private val analyze = GameAnalyzeViewModel(gameBoard.pos)

    /**
     * used for storing our analyze coroutine
     * gets force-stopped when no longer needed
     */
    private var solvingJob: Job? = null

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

    override fun clearTheScene() {
        solvingJob?.cancel()
        CacheUtils.position = gameBoard.pos.value
    }

    /**
     * launches bot actions against player
     */
    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun launchBot() {
        CoroutineScope(botScope).launch {
            while (true) {
                if (!gameBoard.pos.value.pieceToMove
                    && gameBoard.pos.value.gameState() != GameUtils.GameState.End
                ) {
                    val solveResultValue = analyze.data.getAnalyzeResult()
                    // TODO: fix this it is going to shoot at your leg soon
                    GlobalScope.launch(Dispatchers.Main) {
                        analyze.data.solveResult.value = solveResultValue
                        gameBoard.gameClickHandler.processMove(analyze.data.solveResult.value!!.last())
                        CacheUtils.resetCachedPositions()
                    }
                }
                delay(500)
            }
        }
    }
}
