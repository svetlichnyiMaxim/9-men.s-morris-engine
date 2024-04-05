package com.example.mensmorris.common.utils

import androidx.compose.runtime.mutableStateOf
import com.example.mensmorris.common.Movement
import com.example.mensmorris.common.Position
import com.example.mensmorris.common.utils.GameUtils.gameStartPosition

/**
 * provides caching utils
 */
object CacheUtils {
    var position: Position = gameStartPosition

    /**
     * we store occurred positions here which massively increases speed
     */
    val occurredPositions: HashMap<String, Pair<List<Movement>, UByte>> = hashMapOf()

    /**
     * used to check if need to reset our cache or it already is
     */
    var hasCacheWithDepth = false

    /**
     * resets all cached positions depth
     * which prevents engine from skipping important moves which have occurred in previous analyzes
     */
    fun wipeCachedPositions() {
        occurredPositions.clear()
        hasCacheWithDepth = false
    }

    /**
     * resets all cached positions depth
     * which prevents engine from skipping important moves which have occurred in previous analyzes
     */
    fun resetCachedPositions() {
        if (!hasCacheWithDepth) {
            return
        }
        occurredPositions.forEach {
            occurredPositions[it.key] = Pair(it.value.first, 0u)
        }
        hasCacheWithDepth = false
    }

    /**
     * used for storing our game analyzes result
     */
    var solveResult = mutableStateOf<List<Movement>>(mutableListOf())
}
