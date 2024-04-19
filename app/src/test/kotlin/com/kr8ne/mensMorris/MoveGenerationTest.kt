package com.kr8ne.mensMorris

import com.kr8ne.mensMorris.common.gameBoard.utils.CacheUtils.wipeCachedPositions
import com.kr8ne.mensMorris.positions.MoveGeneration
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MoveGenerationTest : MoveGeneration() {
    @Test
    fun `generation 1`() {
        assertEquals(position1.generateMoves(), position1Result)
    }

    @Test
    fun `generation 2`() {
        assertEquals(position2.generateMoves(), position2Result)
    }

    @Test
    fun `generation 3`() {
        assertEquals(position3.generateMoves(), position3Result)
    }

    @Test
    fun `generation 4`() {
        assertEquals(position4.generateMoves(), position4Result)
    }

    @Test
    fun `generate 5`() {
        assertEquals(position5.generateMoves(), position5Result)
    }

    @Test
    fun `generate 6`() {
        assertEquals(position6.generateMoves(), position6Result)
    }

    @AfterEach
    fun clearCache() {
        wipeCachedPositions()
    }
}
