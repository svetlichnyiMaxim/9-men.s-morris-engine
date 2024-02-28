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
        occurredPositions.clear()
        val ourResult = position1.solve(2u)
        ourResult.second!!.displayAsPositions(position1)
        TestCase.assertEquals(ourResult, correctResult1)
        occurredPositions.clear()
    }

    @Test
    fun `winning test2`() {
        occurredPositions.clear()
        val ourResult = position2.solve(2u)
        ourResult.second?.displayAsPositions(position2)
        TestCase.assertEquals(ourResult.first, correctResult2.first)
        TestCase.assertEquals(ourResult.second!!.size, correctResult2.second.size)
        TestCase.assertEquals(ourResult.second, correctResult2.second)
        occurredPositions.clear()
    }

    @Test
    fun `winning test3`() {
        occurredPositions.clear()
        val ourResult = position3.solve(2u)
        ourResult.second!!.toPositions(position3).forEach {
            it.display()
        }
        TestCase.assertEquals(ourResult, correctResult3)
        occurredPositions.clear()
    }
}
