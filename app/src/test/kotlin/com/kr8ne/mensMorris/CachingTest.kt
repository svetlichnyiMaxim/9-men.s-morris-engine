package com.kr8ne.mensMorris

import com.kr8ne.mensMorris.common.game.Position
import com.kr8ne.mensMorris.common.game.utils.CacheUtils
import com.kr8ne.mensMorris.common.game.utils.CacheUtils.occurredPositions
import com.kr8ne.mensMorris.positions.Caching
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class CachingTest : Caching() {
    @Test
    fun `cache 0`() {
        position.solve(0u)
        assertEquals(occurredPositions.size, 0)
    }

    @Test
    fun `cache 1`() {
        position.solve(1u)
        assertEquals(occurredPositions.size, 1)
    }

    @Test
    fun `cache 2`() {
        position.solve(2u)
        assertEquals(occurredPositions.size, 8)
    }

    @Test
    fun `cache 3`() {
        position.solve(3u)
        assertEquals(occurredPositions.size, 42)
    }

    @Test
    fun `cache 4`() {
        position.solve(4u)
        assertEquals(occurredPositions.size, 209)
    }

    @Test
    fun `cache 5`() {
        position.solve(5u)
        assertEquals(occurredPositions.size, 822)
    }

    @Test
    fun `cache 6`() {
        position.solve(6u)
        assertEquals(occurredPositions.size, 3070)
    }

    @Test
    fun `check cache using`() {
        position.solve(4u).second
        assertEquals(position.solve(3u).second, mutableListOf<Position>())
        assertEquals(position.solve(4u).second, mutableListOf<Position>())
        assertNotEquals(position.solve(5u).second, mutableListOf<Position>())
    }

    @Test
    fun `check hash collisions`() {
        val generatedPositions: MutableMap<Position, Long> = mutableMapOf()
        position.generateMoves(ignoreCache = true).forEach {
            val pos = it.producePosition(position)
            generatedPositions[pos] = pos.longHashCode()
        }
        val usedHashes = mutableMapOf<Long, Position>()
        generatedPositions.forEach {
            if (usedHashes[it.value] != null) {
                assertEquals(usedHashes[it.value]!!, it.key)
            } else {
                usedHashes[it.value] = it.key
            }
        }
    }

    @AfterEach
    fun clearCache() {
        CacheUtils.wipeCachedPositions()
    }
}
