package com.example.mensmorris.domain.impl.tutorial

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.mensmorris.common.AppTheme
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.model.ViewModelInterface
import com.example.mensmorris.model.impl.tutorial.IndicatorsTutorialViewModel
import kotlin.math.roundToInt

/**
 * screen that shows tutorial on how to play this game
 */
class TutorialScreen(
    private val zIndex: MutableState<Float>,
    private val alpha: MutableState<Float>
) :
    ScreenModel {
    /**
     * stores order of tutorials (used for slider)
     */
    private val screensOrder =
        listOf<ViewModelInterface>(
            IndicatorsTutorialViewModel { switchToNextScreen() }
        )

    /**
     * our current screen with tutorial
     */
    private var currentScreen = screensOrder.first()

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
        BoxWithConstraints {
            val swipeState = rememberSwipeableState(SwipeStates.FULL)
            zIndex.value = if (swipeState.currentValue != SwipeStates.CLOSED) 1f else -1f
            val sizePx =
                with(LocalDensity.current) { this@BoxWithConstraints.maxHeight.toPx() * 0.85f }
            val alphaValue = 1 - swipeState.offset.value / sizePx
            alpha.value = alphaValue
            val anchors = mapOf(
                0f to SwipeStates.FULL, 0.1f to SwipeStates.OPENING, sizePx to SwipeStates.CLOSED
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .swipeable(
                        state = swipeState,
                        anchors = anchors,
                        thresholds = { _, _ -> FractionalThreshold(0.2f) },
                        orientation = Orientation.Vertical
                    )
                    .swipeable(
                        state = swipeState,
                        anchors = anchors,
                        thresholds = { _, _ -> FractionalThreshold(0.2f) },
                        orientation = Orientation.Vertical
                    ),
            ) {
                Box(modifier = Modifier
                    .graphicsLayer {
                        alpha = alphaValue
                    }
                    .offset {
                        IntOffset(
                            0, swipeState.offset.value.roundToInt()
                        )
                    }) {
                    AppTheme {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            currentScreen.InvokeRender()
                        }
                    }
                }
            }
        }
    }

    /**
     * switches to the next available screen
     */
    private fun switchToNextScreen() {
        val currentIndex = screensOrder.indexOf(currentScreen)
        currentScreen = screensOrder[(currentIndex + 1) % screensOrder.size]
    }
}
