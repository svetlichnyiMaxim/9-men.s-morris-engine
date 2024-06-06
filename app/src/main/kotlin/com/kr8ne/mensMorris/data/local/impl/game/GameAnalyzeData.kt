package com.kr8ne.mensMorris.data.local.impl.game

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.kr8ne.mensMorris.Position
import com.kr8ne.mensMorris.cache.Cache
import com.kr8ne.mensMorris.data.local.interfaces.DataModel
import com.kr8ne.mensMorris.move.Movement
import kotlin.math.max

/**
 * data for game analyze
 */
class GameAnalyzeData(
    /**
     * position in our analyze
     */
    val pos: MutableState<Position>
) : DataModel {

    /**
     * shows if this position was analyzed or not
     */
    private var previousAnalyzedPosition: Position? = null

    /**
     * search depth
     */
    val depth: MutableIntState = mutableIntStateOf(3)

    /**
     * result of position analyze (best move)
     */
    val solveResult: MutableState<List<Movement>> = mutableStateOf(listOf())

    /**
     * decreases search depth
     */
    fun decreaseDepth() {
        depth.intValue = max(0, depth.intValue - 1)
        stopAnalyze()
    }

    /**
     * increases search depth
     */
    fun increaseDepth() {
        depth.intValue++
        stopAnalyze()
    }

    /**
     * starts board analyze
     */
    fun startAnalyze() {
        val solveResultValue = getAnalyzeResult() ?: return
        solveResult.value = solveResultValue
    }

    /**
     * gets analyze result (winning sequence)
     */
    fun getAnalyzeResult(ignoreCache: Boolean = false): MutableList<Movement>? {
        if (previousAnalyzedPosition == pos.value && !ignoreCache) {
            return null
        }
        previousAnalyzedPosition = pos.value
        return pos.value.solve(depth.intValue.toUByte()).second
    }

    /**
     * hides analyze gui and delete it's result
     */
    private fun stopAnalyze() {
        Cache.resetCacheDepth()
        solveResult.value = mutableListOf()
    }
}
