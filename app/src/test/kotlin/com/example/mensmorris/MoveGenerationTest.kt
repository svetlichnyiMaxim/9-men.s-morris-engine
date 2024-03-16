package com.example.mensmorris

import com.example.mensmorris.game.wipeCachedPositions
import com.example.mensmorris.positions.MoveGeneration
import junit.framework.TestCase
import org.junit.Test

class MoveGenerationTest : MoveGeneration() {
    @Test
    fun `generation 1`() {
        TestCase.assertEquals(position1.generateMoves(), position1Result)
        wipeCachedPositions()
    }

    @Test
    fun `generation 2`() {
        TestCase.assertEquals(position2.generateMoves(), position2Result)
        wipeCachedPositions()
    }

    @Test
    fun `generation 3`() {
        TestCase.assertEquals(position3.generateMoves(), position3Result)
        wipeCachedPositions()
    }

    @Test
    fun `generation 4`() {
        TestCase.assertEquals(position4.generateMoves(), position4Result)
        wipeCachedPositions()
    }

    @Test
    fun `generate 5`() {
        TestCase.assertEquals(position5.generateMoves(), position5Result)
        wipeCachedPositions()
    }

    @Test
    fun `generate 6`() {
        TestCase.assertEquals(position6.generateMoves(), position6Result)
        wipeCachedPositions()
    }
}
