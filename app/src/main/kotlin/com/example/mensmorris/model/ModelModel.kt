package com.example.mensmorris.model

import androidx.compose.runtime.Composable
import com.example.mensmorris.common.utils.CoroutineUtils
import com.example.mensmorris.data.DataModel
import com.example.mensmorris.domain.ScreenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

/**
 * model for our models
 * TODO: rename this
 */
interface ModelModel {
    /**
     * screen we use for rendering
     */
    var render: ScreenModel

    /**
     * data we need
     */
    val data: DataModel

    /**
     * starts backend tasks
     */
    fun invokeBackend() {
        CoroutineScope(CoroutineUtils.defaultDispatcher).async {
            data.invokeBackend()
        }
    }

    /**
     * stops backend
     */
    fun shutDownBackend() {
        data.clearTheScene()
    }

    /**
     * starts render
     */
    @Composable
    fun InvokeRender() {
        render.InvokeRender()
    }
}
