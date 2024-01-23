package com.example.mensmorris.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.mensmorris.ui.screens.GameEnd
import com.example.mensmorris.ui.screens.MainPage


val BUTTON_WIDTH = 35.dp

lateinit var mainActivity: MainActivity
var currentScreen: Screen = Screen.MainGame
    set(value) {
        mainActivity.setContent {
            ScreenSwitcher()
        }
        field = value
    }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = this
        mainActivity.setContent {
            ScreenSwitcher()
        }
    }
}

@Composable
fun ScreenSwitcher() {
    when (currentScreen) {
        Screen.MainGame -> {
            render {
                AppTheme {
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

enum class Screen {
    MainGame, EndGame
}