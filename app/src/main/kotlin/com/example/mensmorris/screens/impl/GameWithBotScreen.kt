package com.example.mensmorris.screens.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import com.example.mensmorris.utils.AnalyzeUtils.startAnalyze
import com.example.mensmorris.utils.CacheUtils.occurredPositions
import com.example.mensmorris.utils.CacheUtils.resetCachedPositions
import com.example.mensmorris.utils.CacheUtils.solveResult
import com.example.mensmorris.utils.CoroutineUtils.stopBot
import com.example.mensmorris.utils.CoroutineUtils.updateBotJob
import com.example.mensmorris.utils.GameUtils
import com.example.mensmorris.utils.GameUtils.gameStartPosition
import com.example.mensmorris.utils.GameUtils.pos
import com.example.mensmorris.utils.processMove
import com.example.mensmorris.AppTheme
import com.example.mensmorris.BUTTON_WIDTH
import com.example.mensmorris.GameBoard
import com.example.mensmorris.GameClickHandler.handleClick
import com.example.mensmorris.Locate
import com.example.mensmorris.screens.GameScreenModel
import kotlinx.coroutines.delay

/**
 * Game main screen
 */
object GameWithBotScreen : GameScreenModel {
    override var gameBoard: GameBoard =
        GameBoard(
            position = pos,
            onClick = { performClickResponse(it) },
            onUndo = { launchBot() }
        )

    @Composable
    override fun InvokeRender() {
        AppTheme {
            gameBoard.Draw()
            DrawPieceCount()
            gameBoard.RenderUndoRedo()
        }
    }

    override fun invokeBackend() {
        pos = gameStartPosition
        occurredPositions.clear()
    }

    /**
     * launches bot actions against player
     */
    private fun launchBot() {
        stopBot()
        var start = true
        updateBotJob {
            while (!pos.pieceToMove && pos.gameState() != GameUtils.GameState.End) {
                startAnalyze()
                if (start) {
                    delay(750)
                    start = false
                }
                processMove(solveResult.value.last())
                resetCachedPositions()
            }
        }
    }

    /**
     * performs needed actions after click
     * @param index index of the clicked element
     */
    private fun performClickResponse(index: Int) {
        if (pos.pieceToMove) {
            handleClick(index)
            launchBot()
        }
    }

    @Composable
    private fun DrawPieceCount() {
        Locate(Alignment.TopStart) {
            Box(
                modifier = Modifier
                    .size(BUTTON_WIDTH * if (pos.pieceToMove) 1.5f else 1f)
                    .background(Color.Green, CircleShape)
                    .alpha(if (pos.freePieces.first == 0.toUByte()) 0f else 1f), Alignment.Center
            ) {
                Text(color = Color.Blue, text = pos.freePieces.first.toString())
            }
        }
        Locate(Alignment.TopEnd) {
            Box(
                modifier = Modifier
                    .size(BUTTON_WIDTH * if (!pos.pieceToMove) 1.5f else 1f)
                    .background(Color.Blue, CircleShape)
                    .alpha(if (pos.freePieces.second == 0.toUByte()) 0f else 1f), Alignment.Center
            ) {
                Text(color = Color.Green, text = pos.freePieces.second.toString())
            }
        }
    }

    override fun clearTheScene() {
        stopBot()
    }
}
