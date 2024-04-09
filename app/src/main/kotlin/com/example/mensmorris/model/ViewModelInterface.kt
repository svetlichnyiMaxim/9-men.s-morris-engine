package com.example.mensmorris.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import com.example.mensmorris.common.utils.backendScope
import com.example.mensmorris.data.DataModel
import com.example.mensmorris.domain.ScreenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * model for our view models
 */
interface ViewModelInterface {
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
        CoroutineScope(backendScope).launch {
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

    /**
     * invokes render and backend using DisposableEffect
     */
    @Composable
    fun <T> Invoke(value: MutableState<T>) {
        this@ViewModelInterface.InvokeRender()
        DisposableEffect(key1 = value) {
            this@ViewModelInterface.invokeBackend()
            onDispose {
                this@ViewModelInterface.shutDownBackend()
            }
        }
    }
}
