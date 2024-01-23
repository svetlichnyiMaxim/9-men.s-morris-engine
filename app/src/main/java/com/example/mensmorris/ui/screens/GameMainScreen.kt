package com.example.mensmorris.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mensmorris.game.Piece
import com.example.mensmorris.game.depth
import com.example.mensmorris.game.pos
import com.example.mensmorris.game.reset
import com.example.mensmorris.game.solveResult
import com.example.mensmorris.game.solving
import com.example.mensmorris.ui.BUTTON_WIDTH
import com.example.mensmorris.ui.DrawBoard
import com.example.mensmorris.ui.Locate
import com.example.mensmorris.ui.RenderMainBoard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.max

@Composable
fun MainPage() {
    RenderMainBoard()
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
    if (solveResult.value.isNotEmpty()) {
        Box(
            modifier = Modifier
                .padding(0.dp, BUTTON_WIDTH * 12, 0.dp, 0.dp)
                .background(Color.DarkGray, RoundedCornerShape(5))
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .weight(1f, false)
                ) {
                    for (i in 0..<solveResult.value.size) {
                        Row {
                            DrawBoard(solveResult.value[i])
                        }
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier
            // BUTTON_WIDTH * 12
            .padding(0.dp, BUTTON_WIDTH * 10.5f, 0.dp, 0.dp)
            .fillMaxSize()
    )
    {
        Locate(Alignment.TopStart) {
            Button(
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape),
                onClick = {
                    depth.intValue = max(0, depth.intValue - 1)
                    reset()
                }) {
                Text("-")
            }
        }
        Locate(Alignment.TopCenter) {
            Button(onClick = {
                solving = CoroutineScope(Dispatchers.Default).launch {
                    solveResult.value = pos.solve(depth.intValue.toUByte()).second
                }
            }) {
                Text("Solve (depth - ${depth.intValue})")
            }
        }
        Locate(Alignment.TopEnd) {
            Button(
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape),
                onClick = {
                    depth.intValue++
                    reset()
                }) {
                Text("+")
            }
        }
    }
}