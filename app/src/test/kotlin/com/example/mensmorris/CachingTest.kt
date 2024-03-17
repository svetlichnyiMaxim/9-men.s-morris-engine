package com.example.mensmorris

import com.example.mensmorris.game.CacheUtils.occurredPositions
import com.example.mensmorris.game.CacheUtils.wipeCachedPositions
import com.example.mensmorris.game.Position
import com.example.mensmorris.positions.Caching
import junit.framework.TestCase
import org.junit.Test

class CachingTest : Caching() {
    @Test
    fun `cache 0`() {
        wipeCachedPositions()
        position.solve(0u)
        TestCase.assertEquals(occurredPositions.size, 0)
        wipeCachedPositions()
    }

    @Test
    fun `cache 1`() {
        wipeCachedPositions()
        position.solve(1u)
        TestCase.assertEquals(occurredPositions.size, 1)
        wipeCachedPositions()
    }

    @Test
    fun `cache 2`() {
        wipeCachedPositions()
        position.solve(2u)
        TestCase.assertEquals(occurredPositions.size, 8)
        wipeCachedPositions()
    }

    @Test
    fun `cache 3`() {
        wipeCachedPositions()
        position.solve(3u)
        TestCase.assertEquals(occurredPositions.size, 42)
        wipeCachedPositions()
    }

    @Test
    fun `cache 4`() {
        wipeCachedPositions()
        position.solve(4u)
        TestCase.assertEquals(occurredPositions.size, 209)
        wipeCachedPositions()
    }

    @Test
    fun `cache 5`() {
        wipeCachedPositions()
        position.solve(5u)
        TestCase.assertEquals(occurredPositions.size, 822)
        wipeCachedPositions()
    }

    @Test
    fun `cache 6`() {
        wipeCachedPositions()
        position.solve(6u)
        TestCase.assertEquals(occurredPositions.size, 3028)
        wipeCachedPositions()
    }

    @Test
    fun `check cache using`() {
        wipeCachedPositions()
        position.solve(4u).second
        TestCase.assertEquals(position.solve(3u).second, mutableListOf<Position>())
        TestCase.assertEquals(position.solve(4u).second, mutableListOf<Position>())
        TestCase.assertNotSame(position.solve(5u).second, mutableListOf<Position>())
        wipeCachedPositions()
    }
}
