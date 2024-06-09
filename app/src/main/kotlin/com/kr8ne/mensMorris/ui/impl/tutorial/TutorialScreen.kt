package com.kr8ne.mensMorris.ui.impl.tutorial

import android.content.res.Resources
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kr8ne.mensMorris.R
import com.kr8ne.mensMorris.ui.impl.tutorial.domain.FlyingMovesTutorialScreen
import com.kr8ne.mensMorris.ui.impl.tutorial.domain.IndicatorsTutorialScreen
import com.kr8ne.mensMorris.ui.impl.tutorial.domain.LoseTutorialScreen
import com.kr8ne.mensMorris.ui.impl.tutorial.domain.NormalMovesTutorialScreen
import com.kr8ne.mensMorris.ui.impl.tutorial.domain.PlacementTutorialScreen
import com.kr8ne.mensMorris.ui.impl.tutorial.domain.RemovalMovesTutorialScreen
import com.kr8ne.mensMorris.ui.impl.tutorial.domain.TriplesTutorialScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.impl.tutorial.TutorialViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * screen that shows tutorial on how to play this game
 */
class TutorialScreen(
    resources: Resources
) : ScreenModel {
    /**
     * stores order of tutorials (used for slider)
     */
    private val tutorialScreens = listOf(
        IndicatorsTutorialScreen(resources),
        LoseTutorialScreen(resources),
        PlacementTutorialScreen(resources),
        NormalMovesTutorialScreen(resources),
        FlyingMovesTutorialScreen(resources),
        TriplesTutorialScreen(resources),
        RemovalMovesTutorialScreen(resources)
    )

    /**
     * rendering of tutorial screens happens here
     */
    @Composable
    private fun InvokeTutorialRendering() {
        val width = LocalConfiguration.current.screenWidthDp
        val scrollState = rememberScrollState()
        val coroutine = rememberCoroutineScope()
        val currentScreenIndex = remember { mutableIntStateOf(0) }

        class CustomFlingBehaviour : FlingBehavior {
            override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
                /**
                NOTE: we can't use width for [animationScrollTo]
                each screen has equal width
                we divide by [tutorialScreens.size - 1] since
                first screen doesn't affect [scrollState.maxValue]
                 */
                val scrollWidth = scrollState.maxValue.toFloat() / (tutorialScreens.size - 1)
                val delta = scrollState.value - scrollWidth * currentScreenIndex.intValue
                if (delta > 0) {
                    currentScreenIndex.intValue++
                    coroutine.launch {
                        scrollState.animateScrollTo((scrollWidth * currentScreenIndex.intValue).roundToInt())
                    }
                }
                if (delta < 0) {
                    currentScreenIndex.intValue--
                    coroutine.launch {
                        scrollState.animateScrollTo((scrollWidth * currentScreenIndex.intValue).roundToInt())
                    }
                }
                return 0f
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(), Alignment.TopCenter
        ) {
            Icon(
                painter = painterResource(id = R.drawable.down_arrow),
                "you can swipe this page down"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(
                    state = scrollState, flingBehavior = CustomFlingBehaviour()
                )
        ) {
            tutorialScreens.forEach {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(width.dp)
                ) {
                    it.InvokeRender()
                }
            }
        }
    }

    @Composable
    override fun InvokeRender() {
        InvokeTutorialRendering()
    }

    override val viewModel = TutorialViewModel()
}
