package com.kroune.mensMorris.ui.impl

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.draggable2D
import androidx.compose.foundation.gestures.rememberDraggable2DState
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kroune.mensMorris.GAME_WITH_BOT_SCREEN
import com.kroune.mensMorris.GAME_WITH_FRIEND_SCREEN
import com.kroune.mensMorris.R
import com.kroune.mensMorris.SEARCHING_ONLINE_GAME_SCREEN
import com.kroune.mensMorris.SIGN_IN_SCREEN
import com.kroune.mensMorris.VIEW_ACCOUNT_SCREEN
import com.kroune.mensMorris.common.AppTheme
import com.kroune.mensMorris.common.triangleShape
import com.kroune.mensMorris.data.remote.Common.jwtToken
import com.kroune.mensMorris.ui.impl.tutorial.TutorialScreen
import com.kroune.mensMorris.ui.interfaces.ScreenModel
import com.kroune.mensMorris.viewModel.impl.WelcomeViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.max
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
) : ScreenModel {

    override lateinit var viewModel: WelcomeViewModel

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
    @OptIn(ExperimentalFoundationApi::class)
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
                val scrollUp =
                    (isWelcome.value && progress < 0.15f) || (!isWelcome.value && progress <= 0.85f)
                isWelcome.value = scrollUp
                coroutine.launch {
                    scrollState.animateScrollTo(
                        if (scrollUp) 0 else scrollState.maxValue,
                        animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                    )
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
                                if (jwtToken != null && viewModel.checkJwtToken()
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
                val offset = remember { mutableStateOf(0f) }
                val length = min(width, height) / 4f
                val canDrag = remember { mutableStateOf(true) }
                Box(
                    modifier = Modifier
                        .size((offset.value / 2f + length).dp)
                        .draggable2D(
                            state = rememberDraggable2DState { delta ->
                                offset.value =
                                    ((-delta.x + delta.y) + offset.value).coerceAtLeast(0f)
                            },
                            enabled = canDrag.value,
                            onDragStopped = {
                                // if we should animate transition to the start pos or
                                // continue animation (switch to another screen)
                                val shouldRollBack =
                                    offset.value / 2f + length < min(width, height) / 2
                                val destination = if (shouldRollBack) 0f else (max(
                                    width,
                                    height
                                ).toFloat() - length) * 2
                                canDrag.value = false
                                coroutine.launch {
                                    animate(
                                        offset.value,
                                        destination
                                    ) { value, _ ->
                                        offset.value = value
                                    }
                                    // TODO: finish this animation
                                    if (!shouldRollBack) {
                                        // TODO: change id
                                        navController?.navigate("$VIEW_ACCOUNT_SCREEN/${1L}")
                                    }
                                    canDrag.value = true
                                }
                            }
                        )
                        .background(Color.DarkGray, triangleShape)
                        .align(Alignment.TopEnd),
                    contentAlignment = { size, space, layoutDirection ->
                        IntOffset(
                            space.width / 2,
                            space.height / 2 - size.height
                        )
                    }
                ) {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .size((length / 2f).dp)
                    ) {
                        if (jwtToken != null) {
                            Icon(painterResource(R.drawable.logged_in), "logged in")
                        } else {
                            Icon(painterResource(R.drawable.no_account), "no account found")
                        }
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
        viewModel = hiltViewModel()
        AppTheme {
            DrawGameModesOptions()
        }
    }
}
