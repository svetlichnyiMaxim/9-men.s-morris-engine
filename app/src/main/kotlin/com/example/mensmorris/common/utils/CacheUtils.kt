package com.example.mensmorris.common.utils

import com.example.mensmorris.common.Movement
import com.example.mensmorris.common.Position
import com.example.mensmorris.common.utils.GameUtils.gameStartPosition
import com.example.mensmorris.data.impl.GameEndData
import java.util.Hashtable

/**
 * provides caching utils
 */
object CacheUtils {
    /**
     * position
     * stores when needed
     * @see GameEndData
     * TODO: remove this
     */
    var position: Position = gameStartPosition

    /**
     * we store occurred positions here which massively increases speed
     */
    val occurredPositions: MutableMap<Long, Pair<List<Movement>, UByte>> = mutableMapOf()

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
        occurredPositions.forEach { (key, value) ->
            occurredPositions[key] = Pair(value.first, 0u)
        }
        hasCacheWithDepth = false
    }
}
