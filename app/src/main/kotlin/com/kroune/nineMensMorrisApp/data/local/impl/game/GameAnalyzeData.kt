package com.kroune.nineMensMorrisApp.data.local.impl.game

import com.kr8ne.mensMorris.Position
import com.kr8ne.mensMorris.move.Movement
import com.kroune.nineMensMorrisApp.data.local.interfaces.DataI
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

    private var depthValue: Int = 4
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
        analyzeJob = CoroutineScope(Dispatchers.Default).launch {
            val newMoves = mutableListOf<Movement>()
            var currentPos = pos.value
            // see https://github.com/detekt/detekt/issues/3566
            // however we can't exit repeat with break
            @Suppress("UnusedPrivateProperty")
            for (i in 1..depthValue) {
                val move = currentPos.findBestMove(depthValue.toUByte()) ?: break
                newMoves.add(move)
                currentPos = move.producePosition(currentPos)
            }
            movementsValue = newMoves.asReversed()
        }
        analyzeJob?.start()
    }

    /**
     * hides analyze gui and delete it's result
     */
    private fun stopAnalyze() {
        analyzeJob?.cancel()
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
