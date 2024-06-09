package com.kr8ne.mensMorris.data.local.impl.game

import com.kr8ne.mensMorris.Position
import com.kr8ne.mensMorris.cache.Cache
import com.kr8ne.mensMorris.data.local.interfaces.DataI
import com.kr8ne.mensMorris.move.Movement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.max

/**
 * data for game analyze
 */
class GameAnalyzeData(
    /**
     * position in our analyze
     */
    val pos: MutableStateFlow<Position>
) : DataI() {

    /**
     * shows if this position was analyzed or not
     */
    private var previousAnalyzedPosition: Position? = null
    private var previousAnalyzeDepth: Int? = null

    private var depthValue: Int = 3
        set(value) {
            field = value
            dataState.value = dataState.value.copy(depth = value)
        }

    @Volatile
    private var movementsValue: List<Movement> = listOf()
        set(value) {
            field = value
            dataState.value = dataState.value.copy(positions = value)
        }

    /**
     * data
     */
    val dataState: MutableStateFlow<GameAnalyzeDataState> =
        MutableStateFlow(GameAnalyzeDataState(movementsValue, depthValue))

    /**
     * decreases search depth
     */
    fun decreaseDepth() {
        depthValue = max(0, depthValue - 1)
        stopAnalyze()
    }

    /**
     * increases search depth
     */
    fun increaseDepth() {
        depthValue++
        stopAnalyze()
    }

    /**
     * current analyze job
     */
    var analyzeJob: Job? = null

    /**
     * starts board analyze
     */
    fun startAnalyze() {
        if (previousAnalyzedPosition == pos.value && previousAnalyzeDepth == depthValue) {
            return
        }
        analyzeJob = CoroutineScope(Dispatchers.Default).launch {
            previousAnalyzedPosition = pos.value
            previousAnalyzeDepth = depthValue
            movementsValue = pos.value.solve(depthValue.toUByte()).second
        }
        analyzeJob?.start()
    }

    /**
     * hides analyze gui and delete it's result
     */
    private fun stopAnalyze() {
        analyzeJob?.cancel()
        Cache.resetCacheDepth()
    }
}

/**
 * represents data
 */
data class GameAnalyzeDataState(
    /**
     * analyze result
     */
    val positions: List<Movement>,
    /**
     * current analyze depth
     */
    val depth: Int
)
