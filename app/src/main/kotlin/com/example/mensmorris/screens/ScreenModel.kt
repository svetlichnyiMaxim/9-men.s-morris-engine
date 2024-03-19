package com.example.mensmorris.screens

import androidx.compose.runtime.Composable

/**
 * creates interface for screen creation
 */
interface ScreenModel {
    /**
     * invokes render part
     * launched on the main thread
     */
    @Composable
    fun InvokeRender()

    /**
     * invokes backend part
     * launched with coroutine
     */
    fun invokeBackend()

    /**
     * clears the scene when leaving the screen
     */
    fun clearTheScene()
}
