package com.example.mensmorris

import com.example.mensmorris.game.occurredPositions
import com.example.mensmorris.positions.Strategy
import junit.framework.TestCase
import org.junit.Test

class StrategyTests : Strategy() {
    @Test
    fun `winning test1`() {
        occurredPositions.clear()
        val ourResult = position1.solve(2u)
        TestCase.assertEquals(ourResult, correctResult1)
        occurredPositions.clear()
    }

    @Test
    fun `winning test2`() {
        occurredPositions.clear()
        val ourResult = position2.solve(2u)
        TestCase.assertEquals(ourResult, correctResult2)
        occurredPositions.clear()
    }

    @Test
    fun `winning test3`() {
        occurredPositions.clear()
        val ourResult = position3.solve(2u)
        TestCase.assertEquals(ourResult, correctResult3)
        occurredPositions.clear()
    }
}
