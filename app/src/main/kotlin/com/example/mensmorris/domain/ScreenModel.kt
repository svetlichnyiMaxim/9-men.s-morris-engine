package com.example.mensmorris.domain

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
}