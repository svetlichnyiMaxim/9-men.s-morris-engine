package com.kroune.nineMensMorrisApp.data.local.impl.game

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.GameState
import com.kr8ne.mensMorris.gameStartPosition
import com.kroune.nineMensMorrisApp.data.local.interfaces.DataI
import com.kroune.nineMensMorrisApp.ui.impl.game.GameBoardScreen
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
) : DataI() {

    /**
     * our game board
     */
    val gameBoard = GameBoardScreen(
        pos = gameStartPosition,
        onClick = { index -> this.response(index) },
        handleUndo = { onUndo() },
        navController = navController
    )

    /**
     * performs needed actions after click
     * @param index index of the clicked element
     */
    private fun GameBoardData.response(index: Int) {
        if (gameBoard.pos.value.pieceToMove) {
            handleClick(index)
            handleHighLighting()
            botJob = viewModelScope.launch {
                while (!gameBoard.pos.value.pieceToMove && gameBoard.pos.value.gameState() != GameState.End) {
                    // TODO: FIX THIS
                    // this line isn't needed, but for some reason
                    // without it, this code executes on the main thread
                    delay(10)
                    launchBot()
                }
            }
        }
    }

    private fun onUndo() {
        botJob?.cancel()
        botJob = viewModelScope.launch {
            delay(800)
            while (!gameBoard.pos.value.pieceToMove && gameBoard.pos.value.gameState() != GameState.End) {
                launchBot()
            }
        }
    }

    private var botJob: Job? = null

    /**
     * launches bot actions against player
     */
    private fun launchBot() {
        val bestMove = gameBoard.pos.value.findBestMove(4u)
        gameBoard.viewModel.data.processMove(bestMove!!)
    }
}
