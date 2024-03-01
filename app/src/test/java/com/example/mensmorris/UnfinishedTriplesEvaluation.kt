package com.example.mensmorris

import com.example.mensmorris.game.Position
import com.example.mensmorris.game.BLUE_
import com.example.mensmorris.game.EMPTY
import com.example.mensmorris.game.GREEN
import junit.framework.TestCase
import org.junit.Test

class UnfinishedTriplesEvaluation {
    private val wonPosition = Position(
        // @formatter:off
        mutableListOf(
            BLUE_,                  BLUE_,                  EMPTY,
                    REEN,           EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        pieceToMove = true
    )

    @Test
    fun `winning position`() {
        TestCase.assertEquals(wonPosition.unfinishedTriples(), Pair(1, 1))
    }

    private val wonPosition1 = Position(
        // @formatter:off
        mutableListOf(
            BLUE_,                  BLUE_,                  EMPTY,
                    GREEN,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        freePieces = Pair(0u, 1u), pieceToMove = true
    )

    @Test
    fun `winning position1`() {
        TestCase.assertEquals(wonPosition1.evaluate(), Pair(-2146483648, 2147483647))
    }

    private val lostPosition = Position(
        // @formatter:off
        mutableListOf(
            BLUE_,                  BLUE_,                  BLUE_,
                    GREEN,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        pieceToMove = false
    )

    @Test
    fun `loosing position`() {
        TestCase.assertEquals(lostPosition.evaluate(), Pair(-2146483648, 2147483647))
    }
}
