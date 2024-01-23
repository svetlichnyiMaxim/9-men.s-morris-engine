package com.example.mensmorris.ui.screens

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
import com.example.mensmorris.game.Piece
import com.example.mensmorris.game.moveHints
import com.example.mensmorris.game.pos
import com.example.mensmorris.ui.AppTheme
import com.example.mensmorris.ui.BUTTON_WIDTH
import com.example.mensmorris.ui.DrawBoard
import com.example.mensmorris.ui.Locate
import com.example.mensmorris.ui.Screen
import com.example.mensmorris.ui.currentScreen


@Composable
fun GameEnd() {
    moveHints.value.clear()
    AppTheme {
        Locate(alignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .padding(0.dp, BUTTON_WIDTH * 0.5f, 0.dp, 0.dp)
                    .fillMaxSize(),
                Alignment.Center
            ) {
                Text(fontSize = 30.sp, text = "Game has ended")
            }
            DrawBoard(pos, onClick = {})
            Locate(Alignment.TopStart) {
                Box(
                    modifier = Modifier
                        .size(BUTTON_WIDTH * if (pos.pieceToMove == Piece.GREEN) 1.5f else 1f)
                        .background(Color.Green, CircleShape), Alignment.Center
                ) {
                    Text(color = Color.Blue, text = pos.freePieces.first.toString())
                }
            }
            Locate(Alignment.TopEnd) {
                Box(
                    modifier = Modifier
                        .size(BUTTON_WIDTH * if (pos.pieceToMove == Piece.BLUE_) 1.5f else 1f)
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
                    currentScreen = Screen.MainGame
                }) {
                    Text("Reset")
                }
            }
        }
    }
}