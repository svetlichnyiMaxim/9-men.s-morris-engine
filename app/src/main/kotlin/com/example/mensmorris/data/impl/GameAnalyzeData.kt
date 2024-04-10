package com.example.mensmorris.data.impl

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.MutableLiveData
import com.example.mensmorris.common.Movement
import com.example.mensmorris.common.Position
import com.example.mensmorris.common.utils.CacheUtils
import com.example.mensmorris.data.DataModel
import kotlin.math.max

/**
 * data for game analyze
 */
class GameAnalyzeData(
    /**
     * position in our analyze
     */
    val pos: MutableLiveData<Position>
) : DataModel {

    /**
     * search depth
     */
    val depth: MutableIntState = mutableIntStateOf(3)

    /**
     * result of position analyze (best move)
     */
    val solveResult: MutableLiveData<List<Movement>> = MutableLiveData()

    override suspend fun invokeBackend() {
        // TODO: Not yet implemented
    }

    override fun clearTheScene() {
        // TODO: Not yet implemented
    }

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
        solveResult.postValue(solveResultValue)
    }

    /**
     * gets analyze result (winning sequence)
     */
    fun getAnalyzeResult(ignoreCache: Boolean = false): MutableList<Movement>? {
        if (CacheUtils.hasCacheWithDepth && !ignoreCache) {
            return null
        }
        CacheUtils.hasCacheWithDepth = true
        return pos.value!!.solve(depth.intValue.toUByte()).second
    }

    /**
     * hides analyze gui and delete it's result
     */
    private fun stopAnalyze() {
        CacheUtils.resetCachedPositions()
        solveResult.value = mutableListOf()
    }
}
