package com.example.mensmorris

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.mensmorris.common.render
import com.example.mensmorris.model.ViewModelInterface
import com.example.mensmorris.model.impl.GameEndViewModel
import com.example.mensmorris.model.impl.GameWithBotViewModel
import com.example.mensmorris.model.impl.GameWithFriendViewModel
import com.example.mensmorris.model.impl.WelcomeViewModel
import com.example.mensmorris.model.impl.tutorial.TutorialViewModel
import kotlinx.coroutines.launch

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
            DisposableEffect(key1 = currentScreen) {
                lifecycleScope.launch {
                    render {
                        currentScreen.value.viewModel.invokeBackend()
                        currentScreen.value.viewModel.InvokeRender()
                    }
                }
                onDispose {
                    // TODO: this isn't working find why and fix
                    currentScreen.value.viewModel.shutDownBackend()
                    previousScreen = currentScreen.value
                }
            }
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
    var viewModel: ViewModelInterface
) {
    /**
     * screen everything starts from
     */
    Welcome(WelcomeViewModel()),

    /**
     * just a normal game
     */
    GameWithFriend(GameWithFriendViewModel()),

    /**
     * no friends :(
     */
    GameWithBot(GameWithBotViewModel()),

    /**
     * when the game has ended
     */
    EndGame(GameEndViewModel())
}
