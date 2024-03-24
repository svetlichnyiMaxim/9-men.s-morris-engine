package com.example.mensmorris.screens.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mensmorris.AppTheme
import com.example.mensmorris.BUTTON_WIDTH
import com.example.mensmorris.DrawGameAnalyze
import com.example.mensmorris.gameBoard.GameBoard
import com.example.mensmorris.Locate
import com.example.mensmorris.render
import com.example.mensmorris.screens.GameScreenModel
import com.example.mensmorris.utils.CacheUtils.resetCachedPositions
import com.example.mensmorris.utils.GameUtils.gameStartPosition
import com.example.mensmorris.utils.GameUtils.pos

/**
 * Game main screen
 */
object GameWithFriendScreen : GameScreenModel {
    override var gameBoard: GameBoard =
        GameBoard(
            position = mutableStateOf(gameStartPosition),
            onClick = { index, func -> func(index) },
            onUndo = {}
        )

    @Composable
    override fun InvokeRender() {
        render {
            AppTheme {
                DrawMainPage()
                gameBoard.Draw()
                gameBoard.RenderUndoRedo()
            }
        }
    }

    override fun invokeBackend() {
        pos = gameStartPosition
        resetCachedPositions()
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
        Box(
            modifier = Modifier
                .padding(0.dp, BUTTON_WIDTH * 10.5f, 0.dp, 0.dp)
                .fillMaxSize()
        ) {
            DrawGameAnalyze()
        }
    }

    override fun clearTheScene() {
        //TODO("Not yet implemented")
    }
}
