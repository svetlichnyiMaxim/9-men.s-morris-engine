package com.example.mensmorris.game

import androidx.compose.runtime.mutableStateOf
import com.example.mensmorris.game.GameUtils.gameStartPosition

/**
 * provides caching utils
 */
object CacheUtils {
    /**
     * we store occurred positions here which massively increases speed
     */
    val occurredPositions: HashMap<String, Pair<List<Movement>, UByte>> = hashMapOf()

    /**
     * used to check if need to reset our cache or it already is
     */
    var hasCache = false

    /**
     * resets all cached positions depth
     * which prevents engine from skipping important moves which have occurred in previous analyzes
     */
    fun wipeCachedPositions() {
        occurredPositions.clear()
        hasCache = false
    }

    /**
     * resets all cached positions depth
     * which prevents engine from skipping important moves which have occurred in previous analyzes
     */
    fun resetCachedPositions() {
        if (!hasCache) {
            return
        }
        occurredPositions.forEach {
            occurredPositions[it.key] = Pair(it.value.first, 0u)
        }
        hasCache = false
    }

    /**
     * used for storing our game analyzes result
     */
    var solveResult = mutableStateOf<List<Movement>>(mutableListOf())

    /**
     * stores current game position
     */
    var gamePosition = mutableStateOf(gameStartPosition)

    /**
     * stores all pieces which can be moved (used for highlighting)
     */
    var moveHints = mutableStateOf(listOf<Int>())

    /**
     * used for storing info of the previous (valid one) clicked button
     */
    var selectedButton = mutableStateOf<Int?>(null)
}