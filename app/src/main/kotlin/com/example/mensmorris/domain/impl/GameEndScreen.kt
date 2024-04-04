package com.example.mensmorris.domain.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mensmorris.common.AppTheme
import com.example.mensmorris.BUTTON_WIDTH
import com.example.mensmorris.common.Locate
import com.example.mensmorris.Screen
import com.example.mensmorris.currentScreen
import com.example.mensmorris.domain.GameScreenModel
import com.example.mensmorris.common.gameBoard.GameBoard
import com.example.mensmorris.common.utils.GameUtils.pos

/**
 * screen that is shown at the end
 */
class GameEndScreen(
    override var gameBoard: GameBoard = GameBoard(mutableStateOf(pos), { _, _ -> }, {})
) : GameScreenModel {

    @Composable
    override fun InvokeRender() {
        AppTheme {
            DrawButtons()
            gameBoard.Draw()
        }
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
                        .background(Color.Green, CircleShape), Alignment.Center
                ) {
                    Text(color = Color.Blue, text = pos.freePieces.first.toString())
                }
            }
            Locate(Alignment.TopEnd) {
                Box(
                    modifier = Modifier
                        .size(BUTTON_WIDTH * if (!pos.pieceToMove) 1.5f else 1f)
                        .background(Color.Blue, CircleShape), Alignment.Center
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
}
