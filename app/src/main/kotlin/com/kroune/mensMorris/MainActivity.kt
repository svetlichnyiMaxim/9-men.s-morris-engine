package com.kroune.mensMorris

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kr8ne.mensMorris.Position
import com.kroune.mensMorris.data.remote.Common
import com.kroune.mensMorris.ui.impl.AppStartAnimationScreen
import com.kroune.mensMorris.ui.impl.WelcomeScreen
import com.kroune.mensMorris.ui.impl.auth.SignInScreen
import com.kroune.mensMorris.ui.impl.auth.SignUpScreen
import com.kroune.mensMorris.ui.impl.game.GameEndScreen
import com.kroune.mensMorris.ui.impl.game.GameWithBotScreen
import com.kroune.mensMorris.ui.impl.game.GameWithFriendScreen
import com.kroune.mensMorris.ui.impl.game.OnlineGameScreen
import com.kroune.mensMorris.ui.impl.game.SearchingForGameScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json

/**
 * shows how thick our pieces & board will be
 */
val BUTTON_WIDTH = 35.dp

/**
 * activity our app is launched from
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * nav controller for this activity
     */
    lateinit var navController: NavHostController

    /**
     * we initialize all important stuff here
     */
    @Suppress("LongMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences(
            "com.kr8ne.mensMorris",
            MODE_PRIVATE
        )
        Common.sharedPreferences = sharedPreferences
        val resources = resources
        setContent {
            navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = LOADING_ANIMATION_SCREEN,
                enterTransition = {
                    fadeIn(initialAlpha = 0.1f)
                },
                exitTransition = {
                    fadeOut()
                }) {
                composable(WELCOME_SCREEN) {
                    WelcomeScreen(navController, sharedPreferences, resources).InvokeRender()
                }
                composable(GAME_WITH_BOT_SCREEN) {
                    GameWithBotScreen(navController = navController).InvokeRender()
                }
                composable(GAME_WITH_FRIEND_SCREEN) {
                    GameWithFriendScreen(navController).InvokeRender()
                }
                composable(
                    "$GAME_END_SCREEN/{posAsString}",
                    arguments = listOf(navArgument("posAsString") { type = NavType.StringType })
                ) { posEntry ->
                    val posAsString = posEntry.arguments!!.getString("posAsString")!!
                    val pos = Json.decodeFromString<Position>(posAsString)
                    GameEndScreen(pos, navController).InvokeRender()
                }
                composable(SIGN_UP_SCREEN) {
                    SignUpScreen(navController, resources).InvokeRender()
                }
                composable(SIGN_IN_SCREEN) {
                    SignInScreen(navController, resources).InvokeRender()
                }
                composable(SEARCHING_ONLINE_GAME_SCREEN) {
                    SearchingForGameScreen(navController, resources).InvokeRender()
                }
                composable(
                    "$ONLINE_GAME_SCREEN/{idValue}",
                    arguments = listOf(navArgument("idValue") { type = NavType.LongType })
                ) { gameEntry ->
                    OnlineGameScreen(
                        gameEntry.arguments!!.getLong("idValue"),
                        navController
                    ).InvokeRender()
                }
                composable(LOADING_ANIMATION_SCREEN) {
                    AppStartAnimationScreen(navController).InvokeRender()
                }
            }
        }
    }
}
