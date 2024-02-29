package com.example.mensmorris.game

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException
import kotlin.math.max

/**
 * decreases search depth
 */
fun decreaseDepth() {
    depth.intValue = max(0, depth.intValue - 1)
    resetAnalyze()
}

/**
 * increases search depth
 */
fun increaseDepth() {
    depth.intValue++
    resetAnalyze()
}

/**
 * starts async board analyze
 */
fun startAsyncAnalyze() {
    solvingJob = CoroutineScope(Dispatchers.Default).async {
        startAnalyze()
    }
}

/**
 * starts board analyze
 */
fun startAnalyze() {
    if (hasCache) {
        return
    }
    hasCache = true
    solveResult.value = pos.solve(depth.intValue.toUByte()).second ?: mutableListOf()
}

/**
 * hides analyze gui and delete it's result
 */
fun resetAnalyze() {
    runBlocking {
        botJob?.cancel()
        solvingJob?.cancel()
    }
    stopBot()
    resetCachedPositions()
    solveResult.value = mutableListOf()
}
