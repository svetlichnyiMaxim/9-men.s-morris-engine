package com.kr8ne.mensMorris.data

import androidx.lifecycle.ViewModel

/**
 * model we use for all data providing classes
 */
interface DataModel {
    /**
     * our parent class view module
     */
    val viewModel: ViewModel

    /**
     * invokes backend part
     * launched with coroutine
     */
    suspend fun invokeBackend() {}
}
