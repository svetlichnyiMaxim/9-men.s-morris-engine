package com.kr8ne.mensMorris.data.interfaces

/**
 * model we use for all data providing classes
 */
interface DataModel {
    /**
     * invokes backend part
     * launched with coroutine
     */
    suspend fun invokeBackend() {}
}
