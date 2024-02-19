package com.example.mensmorris.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.mensmorris.ui.Locate
import com.example.mensmorris.ui.Screen
import com.example.mensmorris.ui.currentScreen

/**
 * this screen is shown at the start of the game
 */
object WelcomeScreen {
    /**
     * a basic start screen
     */
    @Composable
    fun StartWelcomeScreen() {
        Locate(Alignment.TopCenter) {
            Row {
                Button(onClick = {
                    currentScreen = Screen.GameWithFriend
                }) {
                    Text(text = "Play with friends")
                }
            }
            Row {
                Button(onClick = {
                    currentScreen = Screen.GameWithFriend
                }) {
                    Text(text = "Play with bot")
                }
            }
        }
    }
}
