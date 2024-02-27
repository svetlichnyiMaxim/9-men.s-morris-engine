package com.example.mensmorris.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.mensmorris.R
import com.example.mensmorris.game.GameState
import com.example.mensmorris.game.botJob
import com.example.mensmorris.game.gameStartPosition
import com.example.mensmorris.game.handleClick
import com.example.mensmorris.game.moveHints
import com.example.mensmorris.game.movesHistory
import com.example.mensmorris.game.occurredPositions
import com.example.mensmorris.game.pos
import com.example.mensmorris.game.resetCachedPositions
import com.example.mensmorris.game.selectedButton
import com.example.mensmorris.game.solveResult
import com.example.mensmorris.game.startAnalyze
import com.example.mensmorris.game.undoneMoveHistory
import com.example.mensmorris.ui.AppTheme
import com.example.mensmorris.ui.BUTTON_WIDTH
import com.example.mensmorris.ui.DrawBoard
import com.example.mensmorris.ui.Locate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Game main screen
 */
object GameWithBotScreen {
    /**
     * launches bot actions against player
     */
    fun launchBot() {
        botJob = CoroutineScope(Dispatchers.Default).launch {
            while (!pos.pieceToMove && pos.gameState() != GameState.End) {
                startAnalyze()
                pos = solveResult.value.last().producePosition(pos)
                resetCachedPositions()
            }
        }
    }

    /**
     * function that invokes entire screen
     */
    @Composable
    fun StartGameWithBot() {
        pos = gameStartPosition
        occurredPositions.clear()
        RenderGameWithBot()
    }

    /**
     * performs needed actions after click
     * @param index index of the clicked element
     */
    fun performClickResponse(index: Int) {
        if (pos.pieceToMove) {
            handleClick(index)
            launchBot()
        }
    }

    /**
     * draws screen during the game
     */
    @Composable
    fun RenderGameWithBot() {
        AppTheme {
            DrawBoard(pos) {
                performClickResponse(it)
            }
            DrawMainPage()
            RenderUndoRedo()
        }
    }

    @Composable
    private fun DrawMainPage() {
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

    /**
     * renders undo buttons
     */
    @Composable
    private fun RenderUndoRedo() {
        Locate(Alignment.BottomStart) {
            Button(
                modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape),
                onClick = {
                    if (!movesHistory.empty()) {
                        undoneMoveHistory.push(movesHistory.peek())
                        movesHistory.pop()
                        pos = movesHistory.lastOrNull() ?: gameStartPosition
                        moveHints.value.clear()
                        selectedButton.value = null
                    }
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.forward), "undo"
                )
            }
        }
        Locate(Alignment.BottomEnd) {
            Button(
                modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape),
                onClick = {
                    if (!undoneMoveHistory.empty()) {
                        movesHistory.push(undoneMoveHistory.peek())
                        undoneMoveHistory.pop()
                        pos = movesHistory.lastOrNull() ?: gameStartPosition
                        moveHints.value.clear()
                        selectedButton.value = null
                    }
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.back), "redo"
                )
            }
        }
    }
}
