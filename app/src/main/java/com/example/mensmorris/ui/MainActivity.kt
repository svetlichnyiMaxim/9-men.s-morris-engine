package com.example.mensmorris.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.mensmorris.game.gamePosition
import com.example.mensmorris.game.gameStartPosition
import com.example.mensmorris.game.occurredPositions
import com.example.mensmorris.ui.screens.GameEnd
import com.example.mensmorris.ui.screens.MainPage


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
var currentScreen: Screen = Screen.MainGame
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
        Screen.MainGame -> {
            render {
                AppTheme {
                    gamePosition.value = gameStartPosition
                    occurredPositions.clear()
                    MainPage()
                }
            }
        }

        Screen.EndGame -> {
            render {
                AppTheme {
                    GameEnd()
                }
            }
        }
    }
}

/**
 * stores our current screen
 */
enum class Screen {
    /**
     * just a normal game
     */
    MainGame,

    /**
     * when the game has ended
     */
    EndGame
}