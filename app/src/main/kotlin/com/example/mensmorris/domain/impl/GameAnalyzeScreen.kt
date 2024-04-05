package com.example.mensmorris.domain.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mensmorris.BUTTON_WIDTH
import com.example.mensmorris.common.Locate
import com.example.mensmorris.common.Position
import com.example.mensmorris.common.gameBoard.GameBoard
import com.example.mensmorris.domain.ScreenModel

class GameAnalyzeScreen(
    val positions: MutableState<List<Position>>,
    val depth: MutableIntState,
    val increaseDepth: () -> Unit,
    val decreaseDepth: () -> Unit,
    val startAnalyze: () -> Unit
) : ScreenModel {

    @Composable
    override fun InvokeRender() {
        DrawGameAnalyze()
    }

    /**
     * draws ui elements for accessing game analyzes
     */
    @Composable
    fun DrawGameAnalyze() {
        if (positions.value.isNotEmpty()) {
            DrawBestLine()
        }
        Locate(Alignment.TopStart) {
            Button(modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape),
                onClick = {
                    decreaseDepth()
                }) {
                Text("-")
            }
        }
        Locate(Alignment.TopCenter) {
            Button(onClick = {
                startAnalyze()
            }) {
                Text("Analyze (depth - ${depth.value})")
            }
        }
        Locate(Alignment.TopEnd) {
            Button(modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape),
                onClick = {
                    increaseDepth()
                }) {
                Text("+")
            }
        }
    }

    /**
     * draws all best moves (main line)
     */
    @Composable
    fun DrawBestLine() {
        Box(
            modifier = Modifier
                .padding(0.dp, BUTTON_WIDTH * 1.5f, 0.dp, 0.dp)
                .background(Color.DarkGray, RoundedCornerShape(5))
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .weight(1f, false)
                ) {
                    positions.value.forEach {
                        Row {
                            GameBoard(remember { mutableStateOf(it) }, { _, _ -> }, {}).Draw()
                        }
                    }
                }
            }
        }
    }
}
