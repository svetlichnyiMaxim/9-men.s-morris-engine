package com.example.mensmorris.game

import com.example.mensmorris.game.CacheUtils.hasCache
import com.example.mensmorris.game.CacheUtils.resetCachedPositions
import com.example.mensmorris.game.CacheUtils.solveResult
import com.example.mensmorris.game.GameUtils.botJob
import com.example.mensmorris.game.GameUtils.depth
import com.example.mensmorris.game.GameUtils.pos
import com.example.mensmorris.game.GameUtils.solvingJob
import com.example.mensmorris.game.GameUtils.stopBot
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
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
 * our dispatcher for most of the coroutines
 */
@OptIn(ExperimentalCoroutinesApi::class)
val defaultDispatcher = Dispatchers.Default.limitedParallelism(10)

/**
 * starts async board analyze
 */
fun startAsyncAnalyze(dispatcher: CoroutineDispatcher = defaultDispatcher) {
    solvingJob = CoroutineScope(dispatcher).async {
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
    solveResult.value = pos.solve(depth.intValue.toUByte()).second
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
