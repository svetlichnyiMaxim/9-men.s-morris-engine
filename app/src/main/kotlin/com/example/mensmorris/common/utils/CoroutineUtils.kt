package com.example.mensmorris.common.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * used for coroutine stuffs
 */
object CoroutineUtils {
    /**
     * our dispatcher for most of the coroutines
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    val defaultDispatcher = Dispatchers.Default.limitedParallelism(10)

    /**
     * updates solving job
     */
    inline fun updateSolvingJob(
        dispatcher: CoroutineDispatcher = defaultDispatcher,
        crossinline func: () -> Unit
    ) {
        solvingJob = CoroutineScope(dispatcher).async {
            func()
        }
    }

    /**
     * updates solving job
     */
    inline fun updateBotJob(
        dispatcher: CoroutineDispatcher = defaultDispatcher,
        crossinline func: suspend CoroutineScope.() -> Unit
    ) {
        botJob = CoroutineScope(dispatcher).async {
            func()
        }
    }

    /**
     * used for storing our analyze coroutine
     * gets force-stopped when no longer needed
     */
    var solvingJob: Job? = null

    /**
     * used for storing our analyze coroutine
     * gets force-stopped when no longer needed
     */
    var botJob: Job? = null

    /**
     * force stops bot
     * TODO: make sure we always shut it down
     */
    fun stopBot() {
        runBlocking {
            botJob?.cancel()
            solvingJob?.cancel()
        }
    }
}
