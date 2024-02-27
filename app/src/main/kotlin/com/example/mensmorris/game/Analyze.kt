package com.example.mensmorris.game

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
