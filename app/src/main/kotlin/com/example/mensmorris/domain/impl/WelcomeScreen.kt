package com.example.mensmorris.domain.impl

import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModel
import com.example.mensmorris.BUTTON_WIDTH
import com.example.mensmorris.activity
import com.example.mensmorris.common.utils.AppTheme
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.model.impl.GameWithBotViewModel
import com.example.mensmorris.model.impl.GameWithFriendViewModel
import com.example.mensmorris.model.impl.tutorial.TutorialViewModel

/**
 * this screen is shown at the start of the game
 */
class WelcomeScreen : ViewModel(), ScreenModel {
    private val tutorialViewModel = TutorialViewModel()

    /**
     * draws game modes options
     */
    @Composable
    private fun DrawGameModesOptions() {
        val zIndexValue = tutorialViewModel.zIndex.floatValue
        val alpha = tutorialViewModel.alpha.floatValue
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .zIndex(zIndexValue)
            ) {
                tutorialViewModel.Invoke()
            }
            AnimatedVisibility(
                visible = alpha == 0f,
                enter = slideInVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ),
                exit = slideOutVertically(
                    animationSpec = tween(
                        durationMillis = 30,
                        easing = FastOutSlowInEasing
                    )
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(
                        BUTTON_WIDTH * 5, Alignment.CenterVertically
                    ), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
                        activity.setContent {
                            GameWithFriendViewModel().Invoke()
                        }
                    }) {
                        Text(text = "Play with friends")
                    }
                    Button(onClick = {
                        activity.setContent {
                            GameWithBotViewModel().Invoke()
                        }
                    }) {
                        Text(text = "Play with bot")
                    }
                }
            }
        }
    }

    @Composable
    override fun InvokeRender() {
        AppTheme {
            DrawGameModesOptions()
        }
    }
}
