package com.kr8ne.mensMorris

import android.content.SharedPreferences
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
import com.kr8ne.mensMorris.common.positionToNuke
import com.kr8ne.mensMorris.ui.impl.AppStartAnimationScreen
import com.kr8ne.mensMorris.ui.impl.WelcomeScreen
import com.kr8ne.mensMorris.ui.impl.auth.SignInScreen
import com.kr8ne.mensMorris.ui.impl.auth.SignUpScreen
import com.kr8ne.mensMorris.ui.impl.game.GameEndScreen
import com.kr8ne.mensMorris.ui.impl.game.GameWithBotScreen
import com.kr8ne.mensMorris.ui.impl.game.GameWithFriendScreen
import com.kr8ne.mensMorris.ui.impl.game.OnlineGameScreen
import com.kr8ne.mensMorris.ui.impl.game.SearchingForGameScreen

/**
 * shows how thick our pieces & board will be
 */
val BUTTON_WIDTH = 35.dp

/**
 * represents current activity
 * used for switching screens
 */
var activity: MainActivity? = null

/**
 * activity our app is launched from
 */
class MainActivity : ComponentActivity() {

    /**
     * nav controller for this activity
     */
    lateinit var navController: NavHostController

    /**
     * shared preferences for this activity
     */
    lateinit var sharedPreferences: SharedPreferences

    /**
     * we initialize all important stuff here
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        sharedPreferences = getSharedPreferences(
            "com.kr8ne.mensMorris",
            MODE_PRIVATE
        )
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
                composable(GAME_END_SCREEN) {
                    GameEndScreen(positionToNuke, navController).InvokeRender()
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
                    println("some text")
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
