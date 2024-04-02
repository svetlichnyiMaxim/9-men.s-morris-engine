package com.example.mensmorris

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import com.example.mensmorris.utils.CoroutineUtils
import com.example.mensmorris.domain.impl.GameWithBotScreen
import com.example.mensmorris.domain.impl.GameWithFriendScreen
import com.example.mensmorris.domain.impl.WelcomeScreen
import com.example.mensmorris.model.ModelModel
import com.example.mensmorris.model.impl.GameEndModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

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
 * stores previous screen
 * TODO: create usage for this
 */
var previousScreen: Screen? = null

/**
 * stores current game screen & updates it on change
 */
var currentScreen: MutableState<Screen> = mutableStateOf(Screen.Welcome)

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
fun ScreenSwitcher(currentScreenValue: MutableState<Screen> = currentScreen) {
    DisposableEffect(key1 = currentScreenValue) {
        CoroutineScope(CoroutineUtils.defaultDispatcher).async {
            currentScreen.value.screen.invokeBackend()
        }
        render {
            currentScreen.value.screen.InvokeRender()
        }
        onDispose {
            currentScreen.value.screen.clearTheScene()
            previousScreen = currentScreen.value
        }
    }
}

/**
 * stores our current screen
 */
enum class Screen(
    /**
     * used for launching a proper screen
     */
    var model: ModelModel
) {
    /**
     * screen everything starts from
     */
    Welcome(WelcomeScreen),

    /**
     * just a normal game
     */
    GameWithFriend(GameWithFriendScreen),

    /**
     * no friends :(
     */
    GameWithBot(GameWithBotScreen),

    /**
     * when the game has ended
     */
    EndGame(GameEndModel())
}
