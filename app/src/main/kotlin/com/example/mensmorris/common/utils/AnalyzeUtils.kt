package com.example.mensmorris.common.utils

import com.example.mensmorris.common.utils.CacheUtils.hasCacheWithDepth
import com.example.mensmorris.common.utils.CacheUtils.resetCachedPositions
import com.example.mensmorris.common.utils.CacheUtils.solveResult
import com.example.mensmorris.common.utils.GameUtils.depth
import com.example.mensmorris.common.utils.GameUtils.pos
import kotlin.math.max

/**
 * provides game analyze utils
 */
object AnalyzeUtils {
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
     * starts board analyze
     */
    fun startAnalyze() {
        if (hasCacheWithDepth) {
            return
        }
        hasCacheWithDepth = true
        solveResult.value = pos.solve(depth.intValue.toUByte()).second
    }

    /**
     * hides analyze gui and delete it's result
     */
    fun resetAnalyze() {
        CoroutineUtils.stopBot()
        resetCachedPositions()
        solveResult.value = mutableListOf()
    }
}
