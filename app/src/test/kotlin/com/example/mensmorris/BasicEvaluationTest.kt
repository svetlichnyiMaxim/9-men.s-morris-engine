package com.example.mensmorris

import com.example.mensmorris.game.LOST_GAME_COST
import com.example.mensmorris.positions.BasicEvaluation
import junit.framework.TestCase
import org.junit.Test

class BasicEvaluationTest : BasicEvaluation() {
    @Test
    fun greenWinning1() {
        TestCase.assertEquals(greenWinning1.evaluate(), Pair(2147483647, LOST_GAME_COST))
    }

    @Test
    fun greenWinning2() {
        TestCase.assertEquals(greenWinning2.evaluate(), Pair(2147483647, LOST_GAME_COST))
    }

    @Test
    fun greenWinning3() {
        TestCase.assertEquals(greenWinning3.evaluate(), Pair(2147483647, LOST_GAME_COST))
    }

    @Test
    fun greenWinning4() {
        TestCase.assertEquals(greenWinning4.evaluate(), Pair(2147483647, LOST_GAME_COST))
    }


    @Test
    fun blueWinning1() {
        TestCase.assertEquals(blueWinning1.evaluate(), Pair(LOST_GAME_COST, 2147483647))
    }

    @Test
    fun blueWinning2() {
        TestCase.assertEquals(blueWinning2.evaluate(), Pair(LOST_GAME_COST, 2147483647))
    }

    @Test
    fun blueWinning3() {
        TestCase.assertEquals(blueWinning3.evaluate(), Pair(LOST_GAME_COST, 2147483647))
    }

    @Test
    fun blueWinning4() {
        TestCase.assertEquals(blueWinning4.evaluate(), Pair(LOST_GAME_COST, 2147483647))
    }
}
