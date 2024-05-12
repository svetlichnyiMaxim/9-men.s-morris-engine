package com.kr8ne.mensMorris.ui.impl.tutorial

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
import androidx.core.content.edit
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.kr8ne.mensMorris.R
import com.kr8ne.mensMorris.activity
import com.kr8ne.mensMorris.common.utils.AppTheme
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.impl.tutorial.viewModels.FlyingMovesTutorialViewModel
import com.kr8ne.mensMorris.viewModel.impl.tutorial.viewModels.IndicatorsTutorialViewModel
import com.kr8ne.mensMorris.viewModel.impl.tutorial.viewModels.LoseTutorialViewModel
import com.kr8ne.mensMorris.viewModel.impl.tutorial.viewModels.NormalMovesTutorialViewModel
import com.kr8ne.mensMorris.viewModel.impl.tutorial.viewModels.PlacementTutorialViewModel
import com.kr8ne.mensMorris.viewModel.impl.tutorial.viewModels.RemovalMovesViewModel
import com.kr8ne.mensMorris.viewModel.impl.tutorial.viewModels.TriplesTutorialViewModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI
import kotlin.math.roundToInt

/**
 * screen that shows tutorial on how to play this game
 */
class TutorialScreen(
    private val progress: MutableState<Float>
) : ScreenModel {
    /**
     * stores order of tutorials (used for slider)
     */
    private val screensOrder = listOf(
        IndicatorsTutorialViewModel(),
        LoseTutorialViewModel(),
        PlacementTutorialViewModel(),
        NormalMovesTutorialViewModel(),
        FlyingMovesTutorialViewModel(),
        TriplesTutorialViewModel(),
        RemovalMovesViewModel()
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
         * tutorial is fully closed
         */
        CLOSED
    }

    /**
     * rendering of tutorial screens happens here
     */
    @Composable
    private fun InvokeTutorialRendering() {
        AppTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                Alignment.TopCenter
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.down_arrow),
                    "you can swipe this page down"
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                currentScreen.value.InvokeRender()
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

    @OptIn(ExperimentalWearMaterialApi::class)
    @Composable
    override fun InvokeRender() {
        val swipeUpState = rememberSwipeableState(SwipeStates.FULL)
        if (swipeUpState.currentValue == SwipeStates.CLOSED) {
            activity.sharedPreferences.edit {
                this.putBoolean("hasSeenTutorial", true)
            }
        }
        BoxWithConstraints {
            val sizePx =
                with(LocalDensity.current) { this@BoxWithConstraints.maxHeight.toPx() * 0.85f }
            progress.value = 1 - swipeUpState.offset.value / sizePx
            val anchorsUp = mapOf(
                0f to SwipeStates.FULL, sizePx to SwipeStates.CLOSED
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .swipeable(
                        state = swipeUpState,
                        anchors = anchorsUp,
                        thresholds = { _, _ -> FractionalThreshold(0.2f) },
                        orientation = Orientation.Vertical,
                    )
            ) {
                // we apply our offset here
                Box(modifier = Modifier
                    .graphicsLayer { alpha = progress.value }
                    .offset { IntOffset(0, swipeUpState.offset.value.roundToInt()) }
                ) {
                    InvokeTutorialRendering()
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
    private fun getNextScreen(): ViewModelI {
        val currentIndex = screensOrder.indexOf(currentScreen.value)
        return screensOrder[(currentIndex + 1) % screensOrder.size]
    }

    /**
     * accesses the previous
     * screen from tutorials list
     */
    private fun getPrevScreen(): ViewModelI {
        val currentIndex = screensOrder.indexOf(currentScreen.value)
        // this prevents any indexing errors
        return screensOrder[(currentIndex + screensOrder.size - 1) % screensOrder.size]
    }
}
