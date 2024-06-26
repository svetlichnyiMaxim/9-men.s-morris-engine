package com.kr8ne.mensMorris

import com.kr8ne.mensMorris.common.game.utils.CacheUtils.wipeCachedPositions
import com.kr8ne.mensMorris.positions.Copy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class CopyTest : Copy() {
    /**
     * this tests we our copy method actually works
     */
    @Test
    fun `copy test1`() {
        val positionCopy = position.copy()
        positionCopy.positions[0] = null
        assertNotEquals(position, positionCopy)
    }

    /**
     * this tests we our copy method actually works
     */
    @Test
    fun `copy test2`() {
        val positionCopy = position.copy()
        positionCopy.positions[10] = null
        assertNotEquals(position, positionCopy)
    }

    @AfterEach
    fun clearCache() {
        wipeCachedPositions()
    }
}
