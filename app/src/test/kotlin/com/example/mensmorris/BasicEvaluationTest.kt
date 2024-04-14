package com.example.mensmorris

import com.example.mensmorris.positions.BasicEvaluation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BasicEvaluationTest : BasicEvaluation() {
    @Test
    fun greenWinning1() {
        assertEquals(greenWinning1.evaluate(), Pair(2147483647, LOST_GAME_COST))
    }

    @Test
    fun greenWinning2() {
        assertEquals(greenWinning2.evaluate(), Pair(2147483647, LOST_GAME_COST))
    }

    @Test
    fun greenWinning3() {
        assertEquals(greenWinning3.evaluate(), Pair(2147483647, LOST_GAME_COST))
    }

    @Test
    fun greenWinning4() {
        assertEquals(greenWinning4.evaluate(), Pair(2147483647, LOST_GAME_COST))
    }


    @Test
    fun blueWinning1() {
        assertEquals(blueWinning1.evaluate(), Pair(LOST_GAME_COST, 2147483647))
    }

    @Test
    fun blueWinning2() {
        assertEquals(blueWinning2.evaluate(), Pair(LOST_GAME_COST, 2147483647))
    }

    @Test
    fun blueWinning3() {
        assertEquals(blueWinning3.evaluate(), Pair(LOST_GAME_COST, 2147483647))
    }

    @Test
    fun blueWinning4() {
        assertEquals(blueWinning4.evaluate(), Pair(LOST_GAME_COST, 2147483647))
    }
}
