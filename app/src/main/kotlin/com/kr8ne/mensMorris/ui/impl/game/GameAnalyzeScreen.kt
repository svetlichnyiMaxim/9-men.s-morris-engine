package com.kr8ne.mensMorris.ui.impl.game

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kr8ne.mensMorris.BUTTON_WIDTH
import com.kr8ne.mensMorris.Position
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.impl.game.GameAnalyzeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

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
                val isDragging = remember { mutableStateOf(false) }
                val offset = remember { mutableFloatStateOf(0f) }
                val animatedOffset = animateFloatAsState(
                    targetValue = if (isDragging.value) offset.floatValue else 0f,
                    label = "offset transition"
                )

                class FlingCustomBehaviour() : FlingBehavior {
                    override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
                        if (offset.floatValue >= BUTTON_WIDTH.value * 1.25f) {
                            viewModel.increaseDepth()
                        }
                        if (offset.floatValue <= -BUTTON_WIDTH.value * 1.25f) {
                            viewModel.decreaseDepth()
                        }
                        offset.floatValue = 0f
                        isDragging.value = false
                        return 0f
                    }
                }
                Text("Analyze")
                // actual composable state that we will show on UI and update in `Scrollable`
                Box(
                    Modifier
                        .height(BUTTON_WIDTH * 3)
                        .width(BUTTON_WIDTH)
                        .scrollable(
                            orientation = Orientation.Vertical,
                            // state for Scrollable, describes how consume scroll amount
                            state = rememberScrollableState { delta ->
                                offset.floatValue =
                                    (offset.floatValue + delta).coerceIn(-1.5f * BUTTON_WIDTH.value..1.5f * BUTTON_WIDTH.value)
                                isDragging.value = true
                                delta
                            },
                            flingBehavior = FlingCustomBehaviour()
                        )
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        Modifier
                            .offset {
                                IntOffset(0, (1.5f * BUTTON_WIDTH.value).roundToInt())
                            }
                            .size(BUTTON_WIDTH),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("${uiState.value.depth + 1}")
                    }
                    Box(
                        Modifier
                            .offset {
                                IntOffset(0, animatedOffset.value.roundToInt())
                            }
                            .background(Color.Red, CircleShape)
                            .size(BUTTON_WIDTH),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "${uiState.value.depth}",
                            // f(0) = 1
                            modifier = Modifier.alpha(
                                (1 - abs(animatedOffset.value) / (1.5f * BUTTON_WIDTH.value))
                            ),
                            style = TextStyle(fontSize = 20.sp)
                        )
                    }
                    Box(
                        Modifier
                            .offset {
                                IntOffset(0, (-1.5f * BUTTON_WIDTH.value).roundToInt())
                            }
                            .size(BUTTON_WIDTH),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("${max(uiState.value.depth - 1, 0)}")
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
