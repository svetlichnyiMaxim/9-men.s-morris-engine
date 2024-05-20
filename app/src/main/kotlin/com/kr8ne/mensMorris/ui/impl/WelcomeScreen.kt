package com.kr8ne.mensMorris.ui.impl

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
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.BUTTON_WIDTH
import com.kr8ne.mensMorris.GAME_WITH_BOT_SCREEN
import com.kr8ne.mensMorris.GAME_WITH_FRIEND_SCREEN
import com.kr8ne.mensMorris.R
import com.kr8ne.mensMorris.SEARCHING_ONLINE_GAME_SCREEN
import com.kr8ne.mensMorris.SIGN_IN_SCREEN
import com.kr8ne.mensMorris.activity
import com.kr8ne.mensMorris.api.Client
import com.kr8ne.mensMorris.common.utils.AppTheme
import com.kr8ne.mensMorris.getString
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.impl.tutorial.TutorialViewModel

/**
 * this screen is shown at the start of the game
 */
class WelcomeScreen(
    /**
     * navigation controller
     */
    val navController: NavHostController?
) : ScreenModel {
    private val hasSeen = activity.sharedPreferences.getBoolean("hasSeenTutorial", false)
    private val tutorialViewModel = TutorialViewModel(if (hasSeen) 0f else -1f)

    /**
     * draws game modes options
     */
    @Composable
    private fun DrawGameModesOptions() {
        val progress = tutorialViewModel.data.progress
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .zIndex(if (progress.floatValue < 1f) -1f else 1f)
            ) {
                tutorialViewModel.Invoke()
            }
            AnimatedVisibility(
                visible = progress.floatValue == 0f,
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
                        navController?.navigate(GAME_WITH_FRIEND_SCREEN)
                    }) {
                        Text(text = getString(R.string.play_game_with_friends))
                    }
                    Button(onClick = {
                        navController?.navigate(GAME_WITH_BOT_SCREEN)
                    }) {
                        Text(text = getString(R.string.play_game_with_bot))
                    }
                    Button(onClick = {
                        if (Client.jwtToken == null) {
                            navController?.navigate(SIGN_IN_SCREEN)
                        } else {
                            navController?.navigate(SEARCHING_ONLINE_GAME_SCREEN)
                        }
                    }) {
                        Text(text = getString(R.string.play_online_game))
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
