package com.kr8ne.mensMorris.common.gameBoard.utils

import com.kr8ne.mensMorris.common.gameBoard.Movement

/**
 * provides caching utils
 */
object CacheUtils {

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
