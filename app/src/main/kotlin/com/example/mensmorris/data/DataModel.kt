package com.example.mensmorris.data

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