package com.example.mensmorris

import com.example.mensmorris.common.gameBoard.utils.CacheUtils
import com.example.mensmorris.positions.Strategy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StrategyTests : Strategy() {
    @Test
    fun `winning test1`() {
        val ourResult = position1.solve(2u)
        assertEquals(ourResult, correctResult1)
    }

    @Test
    fun `winning test2`() {
        val ourResult = position2.solve(2u)
        assertEquals(ourResult, correctResult2)
    }

    @Test
    fun `winning test3`() {
        val ourResult = position3.solve(2u)
        assertEquals(ourResult, correctResult3)
    }

    @AfterEach
    fun clearCache() {
        CacheUtils.wipeCachedPositions()
    }
}
