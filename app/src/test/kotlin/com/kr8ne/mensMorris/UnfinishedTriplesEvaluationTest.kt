package com.kr8ne.mensMorris

import com.kr8ne.mensMorris.positions.UnfinishedTriplesEvaluation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
class UnfinishedTriplesEvaluationTest : UnfinishedTriplesEvaluation() {
    @Test
    fun draw1() {
        assertEquals(
            draw1.triplesEvaluation().first, Pair(1, 1)
        )
    }

    @Test
    fun greenWinning1() {
        assertEquals(
            greenWinning1.triplesEvaluation().first, Pair(1, 1)
        )
    }

    @Test
    fun greenWinning2() {
        assertEquals(
            greenWinning2.triplesEvaluation().first, Pair(1, 0)
        )
    }
}
