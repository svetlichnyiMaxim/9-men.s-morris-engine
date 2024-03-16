package com.example.mensmorris

import com.example.mensmorris.positions.AdvanceEvaluation
import junit.framework.TestCase
import org.junit.Test

class AdvanceEvaluationTest : AdvanceEvaluation() {
    @Test
    fun greenWinning1() {
        TestCase.assertEquals(greenWinning1.evaluate(), Pair(195, -595))
    }

    @Test
    fun draw1() {
        TestCase.assertEquals(draw1.evaluate(), Pair(0, 0))
    }

    @Test
    fun blueWinning1() {
        TestCase.assertEquals(blueWinning1.evaluate(), Pair(-2405, 1605))
    }

    @Test
    fun blueWinning2() {
        TestCase.assertEquals(blueWinning2.evaluate(), Pair(-2000, 2000))
    }
}
