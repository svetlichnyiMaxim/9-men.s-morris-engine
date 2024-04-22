package com.kr8ne.mensMorris.data.impl

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.kr8ne.mensMorris.common.gameBoard.Movement
import com.kr8ne.mensMorris.common.gameBoard.Position
import com.kr8ne.mensMorris.common.gameBoard.utils.CacheUtils
import com.kr8ne.mensMorris.data.interfaces.DataModel
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
        if (CacheUtils.hasCacheWithDepth && !ignoreCache) {
            return null
        }
        CacheUtils.hasCacheWithDepth = true
        return pos.value.solve(depth.intValue.toUByte()).second
    }

    /**
     * hides analyze gui and delete it's result
     */
    private fun stopAnalyze() {
        CacheUtils.resetCachedPositions()
        solveResult.value = mutableListOf()
    }
}
