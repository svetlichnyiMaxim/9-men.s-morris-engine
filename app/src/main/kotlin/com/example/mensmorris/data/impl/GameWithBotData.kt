package com.example.mensmorris.data.impl

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mensmorris.common.gameBoard.GameBoard
import com.example.mensmorris.common.gameBoard.utils.CacheUtils
import com.example.mensmorris.common.gameBoard.utils.GameState
import com.example.mensmorris.common.gameBoard.utils.gameStartPosition
import com.example.mensmorris.data.DataModel
import com.example.mensmorris.data.GameBoardInterface
import com.example.mensmorris.model.impl.GameAnalyzeViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * data for game with bot screen
 */
class GameWithBotData(override val viewModel: ViewModel) : DataModel, GameBoardInterface {
    override val gameBoard = GameBoard(pos = mutableStateOf(gameStartPosition),
        onClick = { index, func -> response(index, func) },
        onUndo = {}
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
                    // TODO: fix this it is going to shoot at your leg soon
                    GlobalScope.launch(Dispatchers.Main) {
                        analyze.data.solveResult.value = solveResultValue
                        // TODO: fix this, it might cause npe
                        gameBoard.processMove(analyze.data.solveResult.value!!.last())
                        CacheUtils.resetCachedPositions()
                    }
                }
                delay(500)
            }
        }
    }
}
