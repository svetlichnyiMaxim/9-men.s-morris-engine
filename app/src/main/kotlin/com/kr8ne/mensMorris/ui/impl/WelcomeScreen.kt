package com.kr8ne.mensMorris.ui.impl

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
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
import kotlinx.coroutines.runBlocking

/**
 * this screen is shown at the start of the game
 */
class WelcomeScreen(
    /**
     * navigation controller
     */
    val navController: NavHostController?
) : ScreenModel {
    private val hasSeen = activity?.sharedPreferences?.getBoolean("hasSeenTutorial", false) ?: true
    private val tutorialViewModel = TutorialViewModel(if (hasSeen) 0f else -1f)

    /**
     * draws game modes options
     */
    @Composable
    private fun DrawGameModesOptions() {
        val height = LocalConfiguration.current.screenHeightDp
        //val width = LocalConfiguration.current.screenWidthDp
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
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.TopEnd)
                ) {
                    val path = Path()
                    path.moveTo(2 * size.width / 3, 0f)
                    path.lineTo(size.width, size.width / 3f)
                    path.lineTo(size.width, 0f)
                    drawPath(path, Color.Red)
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    contentAlignment = Alignment.TopEnd
                ) {
                    if (Client.jwtToken != null) {
                        IconButton(
                            onClick = {
                                Client.jwtToken = null
                                Client.gameId = null
                                //navController?.navigate(VIEW_ACCOUNT_SCREEN)
                            }
                        ) {
                            Icon(painterResource(R.drawable.logged_in), "logged in")
                        }
                    } else {
                        IconButton(
                            onClick = {
                                navController?.navigate(SIGN_IN_SCREEN)
                            }
                        ) {
                            Icon(painterResource(R.drawable.no_account), "no account found")
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = (height * 0.2).dp, bottom = (height * 0.2).dp),
                    verticalArrangement = Arrangement.spacedBy(
                        (height * 0.05).dp,
                        Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
                            navController?.navigate(GAME_WITH_FRIEND_SCREEN)
                        },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonColors(Color.Black, Color.Black, Color.Gray, Color.Gray)
                    ) {
                        Text(
                            text = getString(R.string.play_game_with_friends),
                            color = Color.White
                        )
                    }
                    Button(
                        onClick = {
                            navController?.navigate(GAME_WITH_BOT_SCREEN)
                        },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonColors(Color.Black, Color.Black, Color.Gray, Color.Gray)
                    ) {
                        Text(
                            text = getString(R.string.play_game_with_bot),
                            color = Color.White
                        )
                    }
                    Button(
                        onClick = {
                            // TODO: rework this
                            runBlocking {
                                if (Client.jwtToken != null && Client.checkJwtToken()
                                        .getOrNull() == true
                                ) {
                                    navController?.navigate(SEARCHING_ONLINE_GAME_SCREEN)
                                } else {
                                    navController?.navigate(SIGN_IN_SCREEN)
                                }
                            }
                        },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonColors(Color.Black, Color.Black, Color.Gray, Color.Gray)
                    ) {
                        Text(
                            text = getString(R.string.play_online_game),
                            color = Color.White
                        )
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
