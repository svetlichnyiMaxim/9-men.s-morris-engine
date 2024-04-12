package com.example.mensmorris.domain.impl.tutorial

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.mensmorris.R
import com.example.mensmorris.common.AppTheme
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.model.ViewModelInterface
import com.example.mensmorris.model.impl.tutorial.FlyingMovesTutorialViewModel
import com.example.mensmorris.model.impl.tutorial.IndicatorsTutorialViewModel
import com.example.mensmorris.model.impl.tutorial.LoseTutorialViewModel
import com.example.mensmorris.model.impl.tutorial.NormalMovesTutorialViewModel
import com.example.mensmorris.model.impl.tutorial.PlacementTutorialViewModel
import kotlin.math.roundToInt

/**
 * screen that shows tutorial on how to play this game
 */
class TutorialScreen(
    private val zIndex: MutableState<Float>, private val alpha: MutableState<Float>
) : ScreenModel {
    /**
     * stores order of tutorials (used for slider)
     * TODO: replace this with a fancy code (example: https://github.com/SkidderMC/FDPClient/blob/b72bb4f493f4fd856ce3bd635f4bdbf43dc42c7b/src/main/java/net/ccbluex/liquidbounce/features/module/ModuleManager.kt#L39)
     */
    @Suppress("LongLine")
    private val screensOrder = listOf<ViewModelInterface>(
        IndicatorsTutorialViewModel(),
        LoseTutorialViewModel(),
        PlacementTutorialViewModel(),
        NormalMovesTutorialViewModel(),
        FlyingMovesTutorialViewModel()
    )

    /**
     * our current screen with tutorial
     */
    private var currentScreen = mutableStateOf(screensOrder.first())

    /**
     * states of swiping tutorial down
     */
    enum class SwipeStates {
        /**
         * tutorial is fully opened
         */
        FULL,

        /**
         * tutorial is getting closed/opened
         */
        OPENING,

        /**
         * tutorial is fully closed
         */
        CLOSED
    }

    @OptIn(ExperimentalWearMaterialApi::class)
    @Composable
    override fun InvokeRender() {
        val swipeUpState = rememberSwipeableState(SwipeStates.FULL)
        zIndex.value = if (swipeUpState.currentValue != SwipeStates.CLOSED) 1f else -1f
        BoxWithConstraints {
            val sizePx =
                with(LocalDensity.current) { this@BoxWithConstraints.maxHeight.toPx() * 0.85f }
            val alphaValue = 1 - swipeUpState.offset.value / sizePx
            alpha.value = alphaValue
            val anchorsUp = mapOf(
                0f to SwipeStates.FULL, 0.1f to SwipeStates.OPENING, sizePx to SwipeStates.CLOSED
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .swipeable(
                        state = swipeUpState,
                        anchors = anchorsUp,
                        thresholds = { _, _ -> FractionalThreshold(0.2f) },
                        orientation = Orientation.Vertical
                    )
            ) {
                // we apply our offset here
                Box(modifier = Modifier
                    .graphicsLayer { alpha = alphaValue }
                    .offset { IntOffset(0, swipeUpState.offset.value.roundToInt()) }
                ) {
                    AppTheme {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            currentScreen.value.InvokeRender()
                        }
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize(), Alignment.BottomStart
                ) {
                    Button(onClick = { switchToPreviousScreen() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.slide_back),
                            "switch to previous tutorial gui"
                        )
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize(), Alignment.BottomEnd
                ) {
                    Button(onClick = {
                        switchToNextScreen()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.slide_forward),
                            "switch to next tutorial gui"
                        )
                    }
                }
            }
        }
    }

    /**
     * switches to the next available screen
     */
    private fun switchToNextScreen() {
        currentScreen.value = getNextScreen()
    }

    /**
     * switches to the next available screen
     */
    private fun switchToPreviousScreen() {
        currentScreen.value = getPrevScreen()
    }

    /**
     *
     */
    private fun getNextScreen(): ViewModelInterface {
        val currentIndex = screensOrder.indexOf(currentScreen.value)
        return screensOrder[(currentIndex + 1) % screensOrder.size]
    }

    /**
     * accesses the previous
     * screen from tutorials list
     */
    private fun getPrevScreen(): ViewModelInterface {
        val currentIndex = screensOrder.indexOf(currentScreen.value)
        // this prevents any indexing errors
        return screensOrder[(currentIndex + screensOrder.size - 1) % screensOrder.size]
    }
}
