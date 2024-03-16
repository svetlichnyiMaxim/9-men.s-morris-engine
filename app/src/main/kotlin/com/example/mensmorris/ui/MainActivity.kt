package com.example.mensmorris.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.mensmorris.render
import com.example.mensmorris.ui.screens.GameEndScreen.GameEnd
import com.example.mensmorris.ui.screens.GameWithBotScreen.StartGameWithBot
import com.example.mensmorris.ui.screens.GameWithFriendScreen.StartGameWithFriend
import com.example.mensmorris.ui.screens.WelcomeScreen.StartWelcomeScreen

/**
 * shows how thick our pieces & board will be
 */
val BUTTON_WIDTH = 35.dp

/**
 * stores main activity
 * used for screen switching
 */
lateinit var mainActivity: MainActivity

/**
 * stores current game screen & updates it on change
 */
var currentScreen: Screen = Screen.WelcomeScreen
    set(value) {
        mainActivity.setContent {
            ScreenSwitcher()
        }
        field = value
    }

/**
 * activity our app is launched from
 */
class MainActivity : ComponentActivity() {
    /**
     * we initialize all important stuff here
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = this
        mainActivity.setContent {
            ScreenSwitcher()
        }
    }
}

/**
 * provides a quicker & better way for switching screens than setTheme { }
 */
@Composable
fun ScreenSwitcher() {
    when (currentScreen) {
        Screen.WelcomeScreen -> {
            render {
                StartWelcomeScreen()
            }
        }

        Screen.GameWithFriend -> {
            render {
                StartGameWithFriend()
            }
        }

        Screen.GameWithBot -> {
            render {
                StartGameWithBot()
            }
        }

        Screen.EndGameScreen -> {
            render {
                GameEnd()
            }
        }
    }
}

/**
 * stores our current screen
 */
enum class Screen {
    /**
     * screen everything starts from
     */
    WelcomeScreen,

    /**
     * just a normal game
     */
    GameWithFriend,

    /**
     * no friends :(
     */
    GameWithBot,

    /**
     * when the game has ended
     */
    EndGameScreen
}
