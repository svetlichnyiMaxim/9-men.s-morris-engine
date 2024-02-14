package com.example.mensmorris

import com.example.mensmorris.game.occurredPositions
import com.example.mensmorris.positions.correctResult1
import com.example.mensmorris.positions.correctResult2
import com.example.mensmorris.positions.correctResult3
import com.example.mensmorris.positions.position1
import com.example.mensmorris.positions.position2
import com.example.mensmorris.positions.position3
import junit.framework.TestCase
import org.junit.Test

class StrategyTests {
    @Test
    fun `winning test1`() {
        val ourResult = position1.solve(2u)
        TestCase.assertEquals(ourResult, correctResult1)
        occurredPositions.clear()
    }

    @Test
    fun `winning test2`() {
        val ourResult = position2.solve(2u)
        ourResult.second.onEach { it.display2() }
        TestCase.assertEquals(ourResult.first, correctResult2.first)
        TestCase.assertEquals(ourResult.second.size, correctResult2.second.size)
        for (i in 0..<ourResult.second.size) {
            TestCase.assertEquals(ourResult.second[i], correctResult2.second[i])
        }
        occurredPositions.clear()
    }

    @Test
    fun `winning test3`() {
        val ourResult = position3.solve(2u)
        TestCase.assertEquals(ourResult, correctResult3)
        occurredPositions.clear()
    }
}
