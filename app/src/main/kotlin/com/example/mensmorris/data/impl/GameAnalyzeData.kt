package com.example.mensmorris.data.impl

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.mensmorris.common.Movement
import com.example.mensmorris.common.Position
import com.example.mensmorris.common.utils.CacheUtils
import com.example.mensmorris.common.utils.CoroutineUtils
import com.example.mensmorris.common.utils.GameUtils
import com.example.mensmorris.data.DataModel
import kotlin.math.max

abstract class GameAnalyzeData(val position: MutableState<Position>) : DataModel {

    val depth: MutableIntState = mutableIntStateOf(3)
    val solveResult: MutableState<List<Movement>> = mutableStateOf(listOf())

    abstract override fun invokeBackend()

    abstract override fun clearTheScene()

    /**
     * decreases search depth
     */
    fun decreaseDepth() {
        depth.intValue = max(0, GameUtils.depth.intValue - 1)
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
        if (CacheUtils.hasCacheWithDepth) {
            return
        }
        CacheUtils.hasCacheWithDepth = true
        solveResult.value =
            position.value.solve(depth.intValue.toUByte()).second
    }

    /**
     * hides analyze gui and delete it's result
     */
    private fun resetAnalyze() {
        CoroutineUtils.stopBot()
        CacheUtils.resetCachedPositions()
        CacheUtils.solveResult.value = mutableListOf()
    }
}
