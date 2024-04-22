package com.kr8ne.mensMorris.domain.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.lifecycle.ViewModel
import com.kr8ne.mensMorris.BUTTON_WIDTH
import com.kr8ne.mensMorris.common.gameBoard.GameBoard
import com.kr8ne.mensMorris.common.gameBoard.Position
import com.kr8ne.mensMorris.domain.interfaces.ScreenModel

/**
 * game analyzing screen
 */
class GameAnalyzeScreen(
    /**
     * list of positions to display
     */
    val positions: MutableState<List<Position>>,
    /**
     * current search depth
     */
    private val depth: MutableIntState,
    /**
     * gets called on depth change
     */
    val increaseDepth: () -> Unit,
    /**
     * gets called on depth change
     */
    val decreaseDepth: () -> Unit,
    /**
     * gets called when "start analyze" button it pressed
     */
    val startAnalyze: () -> Unit,
    /**
     * gets called on render invoke
     * needs to be here cause it is @Composable
     */
    val invokeTransformation: @Composable () -> Unit,
) : ViewModel(), ScreenModel {

    @Composable
    override fun InvokeRender() {
        invokeTransformation()
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
        Box(
            modifier = Modifier
                .fillMaxWidth(), Alignment.TopStart
        ) {
            Button(modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape),
                onClick = {
                    decreaseDepth()
                }) {
                Text("-")
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(), Alignment.TopCenter
        ) {
            Button(onClick = {
                startAnalyze()
            }) {
                Text("Analyze (depth - ${depth.intValue})")
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(), Alignment.TopEnd
        ) {
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
                            GameBoard(
                                remember { mutableStateOf(it) },
                                { _, _ -> },
                                {},
                                navController = null
                            ).RenderBoard()
                        }
                    }
                }
            }
        }
    }
}
