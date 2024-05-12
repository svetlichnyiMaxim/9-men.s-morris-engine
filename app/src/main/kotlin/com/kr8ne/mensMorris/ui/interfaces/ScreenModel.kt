package com.kr8ne.mensMorris.ui.interfaces

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
