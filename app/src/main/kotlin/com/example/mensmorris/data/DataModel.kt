package com.example.mensmorris.data

/**
 * model we use for all data providing classes
 */
interface DataModel {
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
