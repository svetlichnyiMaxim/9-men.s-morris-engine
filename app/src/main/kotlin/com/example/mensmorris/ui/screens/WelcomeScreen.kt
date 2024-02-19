package com.example.mensmorris.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mensmorris.ui.BUTTON_WIDTH
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
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(
                BUTTON_WIDTH * 5, Alignment.CenterVertically
            ),
            Alignment.CenterHorizontally,
        ) {
            Button(onClick = {
                currentScreen = Screen.GameWithFriend
            }) {
                Text(text = "Play with friends")
            }
            Button(onClick = {
                currentScreen = Screen.GameWithFriend
            }) {
                Text(text = "Play with bot")
            }
        }
    }
}
