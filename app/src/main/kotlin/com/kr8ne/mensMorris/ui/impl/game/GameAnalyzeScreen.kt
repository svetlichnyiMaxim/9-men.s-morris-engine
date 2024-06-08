package com.kr8ne.mensMorris.ui.impl.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kr8ne.mensMorris.BUTTON_WIDTH
import com.kr8ne.mensMorris.Position
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.impl.game.GameAnalyzeViewModel
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * game analyzing screen
 */
class GameAnalyzeScreen(
    val pos: MutableStateFlow<Position>
) : ScreenModel {
    override val viewModel = GameAnalyzeViewModel(pos)

    @Composable
    override fun InvokeRender() {
        DrawGameAnalyze()
    }

    /**
     * draws ui elements for accessing game analyzes
     */
    @Composable
    fun DrawGameAnalyze() {
        val uiState = viewModel.uiState.collectAsState()
        val positions = uiState.value.positions
        if (positions.isNotEmpty()) {
            DrawBestLine(positions)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(), Alignment.TopCenter
        ) {
            Button(onClick = {
                viewModel.startAnalyze()
            }) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Analyze")
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                viewModel.decreaseDepth()
                            },
                            colors = ButtonColors(
                                Color(177, 134, 255, 50),
                                Color.White,
                                Color.Unspecified,
                                Color.Unspecified
                            )
                        ) {
                            Text("-", fontSize = 30.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("depth - ${uiState.value.depth}", fontSize = 13.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(
                            onClick = {
                                viewModel.increaseDepth()
                            },
                            colors = ButtonColors(
                                Color(177, 134, 255, 50),
                                Color.White,
                                Color.Unspecified,
                                Color.Unspecified
                            )
                        ) {
                            Text("+", fontSize = 22.sp)
                        }
                    }
                }
            }
        }
    }

    /**
     * draws all best moves (main line)
     */
    @Composable
    fun DrawBestLine(positions: List<Position>) {
        Box(
            modifier = Modifier
                .padding(0.dp, BUTTON_WIDTH * 3f, 0.dp, 0.dp)
                .background(Color.DarkGray, RoundedCornerShape(5))
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .weight(1f, false)
                ) {
                    positions.forEach {
                        println("DEBUG \n $it \n DEBUG")
                        GameBoardScreen(
                            it,
                            onClick = {},
                            navController = null
                        ).InvokeRender()
                    }
                }
            }
        }
    }
}
