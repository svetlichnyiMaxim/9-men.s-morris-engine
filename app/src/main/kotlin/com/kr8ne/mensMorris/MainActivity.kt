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
import com.kr8ne.mensMorris.viewModel.impl.AppStartAnimationViewModel
import com.kr8ne.mensMorris.viewModel.impl.WelcomeViewModel
import com.kr8ne.mensMorris.viewModel.impl.auth.SignInViewModel
import com.kr8ne.mensMorris.viewModel.impl.auth.SignUpViewModel
import com.kr8ne.mensMorris.viewModel.impl.game.GameEndViewModel
import com.kr8ne.mensMorris.viewModel.impl.game.GameWithBotViewModel
import com.kr8ne.mensMorris.viewModel.impl.game.GameWithFriendViewModel
import com.kr8ne.mensMorris.viewModel.impl.game.OnlineGameViewModel
import com.kr8ne.mensMorris.viewModel.impl.game.SearchingForGameViewModel

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
                    WelcomeViewModel(navController, sharedPreferences, resources).Invoke()
                }
                composable(GAME_WITH_BOT_SCREEN) {
                    GameWithBotViewModel(navController).Invoke()
                }
                composable(GAME_WITH_FRIEND_SCREEN) {
                    GameWithFriendViewModel(navController).Invoke()
                }
                composable(GAME_END_SCREEN) {
                    GameEndViewModel(positionToNuke, navController).Invoke()
                }
                composable(SIGN_UP_SCREEN) {
                    SignUpViewModel(navController, resources).Invoke()
                }
                composable(SIGN_IN_SCREEN) {
                    SignInViewModel(navController, resources).Invoke()
                }
                composable(SEARCHING_ONLINE_GAME_SCREEN) {
                    SearchingForGameViewModel(navController, resources).Invoke()
                }
                composable(
                    "$ONLINE_GAME_SCREEN/{idValue}",
                    arguments = listOf(navArgument("idValue") { type = NavType.LongType })
                ) { gameEntry ->
                    println("some text")
                    OnlineGameViewModel(
                        navController,
                        gameEntry.arguments!!.getLong("idValue")
                    ).Invoke()
                }
                composable(LOADING_ANIMATION_SCREEN) {
                    AppStartAnimationViewModel(navController).Invoke()
                }
            }
        }
    }
}
