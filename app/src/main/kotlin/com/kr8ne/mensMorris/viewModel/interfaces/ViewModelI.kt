package com.kr8ne.mensMorris.viewModel.interfaces

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kr8ne.mensMorris.data.interfaces.DataModel
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import kotlinx.coroutines.launch

/**
 * model for our view models
 */
abstract class ViewModelI : ViewModel() {
    /**
     * screen we use for rendering
     */
    abstract val render: ScreenModel

    /**
     * data we need
     */
    abstract val data: DataModel

    /**
     * starts backend tasks
     */
    suspend fun invokeBackend() {
        data.invokeBackend()
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
    fun Invoke() {
        LaunchedEffect(key1 = true) {
            viewModelScope.launch {
                this@ViewModelI.invokeBackend()
            }
        }
        this@ViewModelI.InvokeRender()
    }
}
