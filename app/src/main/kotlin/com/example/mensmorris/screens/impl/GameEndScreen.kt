package com.example.mensmorris.screens.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mensmorris.utils.CacheUtils.moveHints
import com.example.mensmorris.utils.GameUtils.pos
import com.example.mensmorris.AppTheme
import com.example.mensmorris.BUTTON_WIDTH
import com.example.mensmorris.GameBoard
import com.example.mensmorris.Locate
import com.example.mensmorris.Screen
import com.example.mensmorris.currentScreen
import com.example.mensmorris.screens.GameScreenModel

/**
 * screen that is shown at the end
 */
object GameEndScreen : GameScreenModel {
    override var gameBoard: GameBoard = GameBoard(pos, {}, {})

    @Composable
    override fun InvokeRender() {
        AppTheme {
            DrawButtons()
            gameBoard.Draw()
        }
    }

    override fun invokeBackend() {
        moveHints.value = arrayListOf()
    }

    /**
     * draws screen after the game has ended
     */
    @Composable
    private fun DrawButtons() {
        Locate(alignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .padding(0.dp, BUTTON_WIDTH * 0.5f, 0.dp, 0.dp)
                    .fillMaxSize(),
                Alignment.Center
            ) {
                Text(fontSize = 30.sp, text = "Game has ended")
            }
            Locate(Alignment.TopStart) {
                Box(
                    modifier = Modifier
                        .size(BUTTON_WIDTH * if (pos.pieceToMove) 1.5f else 1f)
                        .background(Color.Green, CircleShape),
                    Alignment.Center
                ) {
                    Text(color = Color.Blue, text = pos.freePieces.first.toString())
                }
            }
            Locate(Alignment.TopEnd) {
                Box(
                    modifier = Modifier
                        .size(BUTTON_WIDTH * if (!pos.pieceToMove) 1.5f else 1f)
                        .background(Color.Blue, CircleShape),
                    Alignment.Center
                ) {
                    Text(color = Color.Green, text = pos.freePieces.second.toString())
                }
            }
            Box(
                modifier = Modifier
                    .padding(0.dp, BUTTON_WIDTH * 10, 0.dp, 0.dp)
                    .fillMaxSize(),
                Alignment.Center
            ) {
                Button(onClick = {
                    currentScreen.value = Screen.Welcome
                }) {
                    Text("Reset")
                }
            }
        }
    }

    override fun clearTheScene() {
        //TODO("Not yet implemented")
    }
}
