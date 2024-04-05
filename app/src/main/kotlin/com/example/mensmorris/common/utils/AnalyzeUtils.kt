package com.example.mensmorris.common.utils

import com.example.mensmorris.common.utils.CacheUtils.resetCachedPositions
import com.example.mensmorris.common.utils.CacheUtils.solveResult

/**
 * provides game analyze utils
 */
object AnalyzeUtils {
    /**
     * hides analyze gui and delete it's result
     */
    fun resetAnalyze() {
        CoroutineUtils.stopBot()
        resetCachedPositions()
        solveResult.value = mutableListOf()
    }
}
