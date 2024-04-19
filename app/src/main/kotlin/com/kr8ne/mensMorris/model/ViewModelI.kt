package com.kr8ne.mensMorris.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kr8ne.mensMorris.common.utils.backendScope
import com.kr8ne.mensMorris.data.DataModel
import com.kr8ne.mensMorris.domain.ScreenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * model for our view models
 */
abstract class ViewModelI : ViewModel() {
    /**
     * screen we use for rendering
     */
    abstract var render: ScreenModel

    /**
     * data we need
     */
    abstract val data: DataModel

    /**
     * starts backend tasks
     */
    fun invokeBackend() {
        CoroutineScope(backendScope).launch {
            data.invokeBackend()
        }
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
            this@ViewModelI.viewModelScope.launch {
                this@ViewModelI.invokeBackend()
            }
        }
        this@ViewModelI.InvokeRender()
    }
}
