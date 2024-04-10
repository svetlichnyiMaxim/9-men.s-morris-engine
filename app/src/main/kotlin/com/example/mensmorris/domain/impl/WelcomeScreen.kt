package com.example.mensmorris.domain.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.example.mensmorris.BUTTON_WIDTH
import com.example.mensmorris.Screen
import com.example.mensmorris.common.AppTheme
import com.example.mensmorris.currentScreen
import com.example.mensmorris.domain.ScreenModel

/**
 * this screen is shown at the start of the game
 */
class WelcomeScreen : ViewModel(), ScreenModel {
    /**
     * draws game modes options
     */
    @Composable
    private fun DrawGameModesOptions() {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(
                BUTTON_WIDTH * 5, Alignment.CenterVertically
            ), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
                currentScreen.value = Screen.GameWithFriend
            }) {
                Text(text = "Play with friends")
            }
            Button(onClick = {
                currentScreen.value = Screen.GameWithBot
            }) {
                Text(text = "Play with bot")
            }
        }
    }

    @Composable
    override fun InvokeRender() {
        AppTheme {
            DrawGameModesOptions()
        }
    }
}
