package com.example.mensmorris

import com.example.mensmorris.positions.UnfinishedTriplesEvaluation
import junit.framework.TestCase
import org.junit.Test

class UnfinishedTriplesEvaluationTest : UnfinishedTriplesEvaluation() {
    @Test
    fun draw1() {
        TestCase.assertEquals(
            draw1.triplesEvaluation().first, Pair(1, 1)
        )
    }

    @Test
    fun greenWinning1() {
        TestCase.assertEquals(
            greenWinning1.triplesEvaluation().first, Pair(1, 1)
        )
    }

    @Test
    fun greenWinning2() {
        TestCase.assertEquals(
            greenWinning2.triplesEvaluation().first, Pair(1, 0)
        )
    }
}
