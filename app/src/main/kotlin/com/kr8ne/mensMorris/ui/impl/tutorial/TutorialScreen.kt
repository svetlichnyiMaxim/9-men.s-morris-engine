package com.kr8ne.mensMorris.ui.impl.tutorial

import android.content.res.Resources
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        val height = LocalConfiguration.current.screenHeightDp
        val coroutine = rememberCoroutineScope()
        val currentScreenIndex = remember { mutableIntStateOf(0) }
        val listState = rememberLazyListState()
        class CustomFlingBehaviour : FlingBehavior {
            override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
                /**
                NOTE: we can't use width for [animationScrollTo]
                each screen has equal width
                we divide by [tutorialScreens.size - 1] since
                first screen doesn't affect [scrollState.maxValue]
                 */
                val scrollWidth = listState.layoutInfo.viewportSize.width
                when {
                    (listState.firstVisibleItemIndex < currentScreenIndex.intValue && listState.firstVisibleItemScrollOffset <= scrollWidth * 0.85) -> {
                        currentScreenIndex.intValue--
                        coroutine.launch {
                            listState.animateScrollToItem(currentScreenIndex.intValue)
                        }
                    }

                    (listState.firstVisibleItemScrollOffset > 0 && listState.firstVisibleItemScrollOffset >= scrollWidth * 0.15) -> {
                        currentScreenIndex.intValue++
                        coroutine.launch {
                            listState.animateScrollToItem(currentScreenIndex.intValue)
                        }
                    }

                    else -> {
                        coroutine.launch {
                            listState.animateScrollToItem(currentScreenIndex.intValue)
                        }
                    }
                }
                return 0f
            }
        }
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(0.dp, width.dp),
            state = listState,
            flingBehavior = CustomFlingBehaviour()
        ) {
            items(count = tutorialScreens.size,
                key = {
                    it
                }) {
                Box(
                    modifier = Modifier
                        .height(height.dp)
                        .width(width.dp)
                ) {
                    tutorialScreens[it].InvokeRender()
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

@Preview(device = "spec:parent=pixel_5")
@Composable
fun prev() {
    TutorialScreen(Resources.getSystem()).InvokeRender()
}

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun prev1() {
    TutorialScreen(Resources.getSystem()).InvokeRender()
}
