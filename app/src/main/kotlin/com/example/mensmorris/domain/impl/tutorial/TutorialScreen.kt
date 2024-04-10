package com.example.mensmorris.domain.impl.tutorial

import androidx.compose.runtime.Composable
import com.example.mensmorris.common.AppTheme
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.model.ViewModelInterface
import com.example.mensmorris.model.impl.tutorial.IndicatorsTutorialViewModel

/**
 * screen that shows tutorial on how to play this game
 */
class TutorialScreen : ScreenModel {
    /**
     * stores order of tutorials (used for slider)
     */
    private val screensOrder =
        listOf<ViewModelInterface>(
            IndicatorsTutorialViewModel { switchToNextScreen() }
        )

    /**
     * our current screen with tutorial
     */
    private var currentScreen = screensOrder.first()

    @Composable
    override fun InvokeRender() {
        AppTheme {
            currentScreen.InvokeRender()
        }
        TODO("Not yet implemented")
    }

    /**
     * switches to the next available screen
     */
    private fun switchToNextScreen() {
        val currentIndex = screensOrder.indexOf(currentScreen)
        currentScreen = screensOrder[(currentIndex + 1) % screensOrder.size]
    }
}
