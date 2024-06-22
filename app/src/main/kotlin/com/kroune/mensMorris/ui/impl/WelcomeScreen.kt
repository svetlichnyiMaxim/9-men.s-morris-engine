package com.kroune.mensMorris.ui.impl

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.kroune.mensMorris.GAME_WITH_BOT_SCREEN
import com.kroune.mensMorris.GAME_WITH_FRIEND_SCREEN
import com.kroune.mensMorris.R
import com.kroune.mensMorris.SEARCHING_ONLINE_GAME_SCREEN
import com.kroune.mensMorris.SIGN_IN_SCREEN
import com.kroune.mensMorris.common.AppTheme
import com.kroune.mensMorris.data.remote.AuthRepositoryImpl
import com.kroune.mensMorris.data.remote.Common.jwtToken
import com.kroune.mensMorris.ui.impl.tutorial.TutorialScreen
import com.kroune.mensMorris.ui.interfaces.ScreenModel
import com.kroune.mensMorris.viewModel.impl.WelcomeViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.min

/**
 * this screen is shown at the start of the game
 */
class WelcomeScreen(
    /**
     * navigation controller
     */
    val navController: NavHostController?,
    private val sharedPreferences: SharedPreferences?,
    private val resources: Resources,
    private val authRepository: AuthRepositoryImpl = AuthRepositoryImpl()
) : ScreenModel {
    private var hasSeen = sharedPreferences?.getBoolean("hasSeenTutorial", false) ?: false
        set(value) {
            if (field != value) {
                sharedPreferences?.edit {
                    this.putBoolean("hasSeenTutorial", value)
                }
                field = value
            }
        }

    /**
     * draws game modes options
     */
    @Composable
    private fun DrawGameModesOptions() {
        val width = LocalConfiguration.current.screenWidthDp
        val height = LocalConfiguration.current.screenHeightDp
        val scrollState = rememberScrollState(if (!hasSeen) Int.MAX_VALUE else 0)
        val isTutorialClosed = remember { derivedStateOf { scrollState.value == 0 } }
        val coroutine = rememberCoroutineScope()
        val isWelcome = remember { mutableStateOf(true) }
        if (isTutorialClosed.value) {
            hasSeen = true
        }
        class CustomFlingBehaviour : FlingBehavior {
            override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
                val progress = scrollState.value.toFloat() / scrollState.maxValue
                if (isWelcome.value) {
                    val scrollDown = progress >= 0.15f
                    isWelcome.value = !scrollDown
                    coroutine.launch {
                        scrollState.animateScrollTo(
                            if (scrollDown) scrollState.maxValue else 0,
                            animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                        )
                    }
                } else {
                    val scrollUp = progress <= 0.85f
                    isWelcome.value = scrollUp
                    coroutine.launch {
                        scrollState.animateScrollTo(
                            if (scrollUp) 0 else scrollState.maxValue,
                            animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                        )
                    }
                }
                return 0f
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = scrollState,
                    flingBehavior = CustomFlingBehaviour()
                )
        ) {
            Box(
                modifier = Modifier
                    .size(width.dp, height.dp),
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val length = min(size.width / 3f, size.height / 3f)
                    val path = Path()
                    path.moveTo(size.width - length, 0f)
                    path.lineTo(size.width, 0f)
                    path.lineTo(size.width, length)
                    drawPath(path, Color(0xFF696969))
                }
                Box(
                    modifier = Modifier
                        .size(width.dp, height.dp)
                        .align(Alignment.Center),
                    contentAlignment = Alignment.TopEnd
                ) {
                    if (jwtToken != null) {
                        IconButton(
                            onClick = {
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
                            text = resources.getString(R.string.play_game_with_friends),
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
                            text = resources.getString(R.string.play_game_with_bot),
                            color = Color.White
                        )
                    }
                    Button(
                        onClick = {
                            // TODO: rework this
                            runBlocking {
                                if (jwtToken != null && authRepository.checkJwtToken()
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
                            text = resources.getString(R.string.play_online_game),
                            color = Color.White
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(height.dp)
            ) {
                TutorialScreen(resources).InvokeRender()
            }
        }
    }

    @Composable
    override fun InvokeRender() {
        AppTheme {
            DrawGameModesOptions()
        }
    }

    override val viewModel = WelcomeViewModel()
}
