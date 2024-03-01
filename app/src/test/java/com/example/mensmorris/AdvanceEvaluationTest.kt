package com.example.mensmorris

import com.example.mensmorris.game.BLUE_
import com.example.mensmorris.game.Position
import com.example.mensmorris.game.EMPTY
import com.example.mensmorris.game.GREEN
import junit.framework.TestCase
import org.junit.Test

class AdvanceEvaluationTest {
    private val wonPosition = Position(
        // @formatter:off
        mutableListOf(
            BLUE_,                  EMPTY,                  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
                            GREEN,  EMPTY,  GREEN,
            EMPTY,  EMPTY,  BLUE_,          EMPTY,  EMPTY,  EMPTY,
                            BLUE_,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        freePieces = Pair(7u, 6u), pieceToMove = true
    )

    @Test
    fun `winning position`() {
        TestCase.assertEquals(wonPosition.evaluate(), Pair(195, -595))
    }

    private val wonPosition1 = Position(
        // @formatter:off
        mutableListOf(
            EMPTY,                  EMPTY,                  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
                            GREEN,  BLUE_,  GREEN,
            EMPTY,  EMPTY,  BLUE_,          EMPTY,  EMPTY,  EMPTY,
                            BLUE_,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        freePieces = Pair(7u, 6u), pieceToMove = true
    )

    @Test
    fun `winning position1`() {
        TestCase.assertEquals(wonPosition1.evaluate(), Pair(0, 0))
    }

    private val lostPosition2 = Position(
        // @formatter:off
        mutableListOf(
            BLUE_,                                    GREEN,                                    EMPTY,
                            EMPTY,                    EMPTY,                   BLUE_,
                                            GREEN,    EMPTY,    GREEN,
            EMPTY,          EMPTY,          BLUE_,              EMPTY,         EMPTY,           EMPTY,
                                            BLUE_,    EMPTY,    EMPTY,
                            EMPTY,                    EMPTY,                   BLUE_,
            EMPTY,                                    EMPTY,                                    EMPTY
        ),
        // @formatter:on
        freePieces = Pair(5u, 5u), pieceToMove = false
    )

    @Test
    fun `loosing position2`() {
        TestCase.assertEquals(lostPosition2.evaluate(), Pair(-2405, 1605))
    }

    private val lostPosition3 = Position(
        // @formatter:off
        mutableListOf(
            BLUE_,                                    GREEN,                                    EMPTY,
                            EMPTY,                    EMPTY,                    EMPTY,
                                            GREEN,    BLUE_,   GREEN,
            EMPTY,          EMPTY,          BLUE_,             EMPTY,           EMPTY,          EMPTY,
                                            BLUE_,    EMPTY,   EMPTY,
                            EMPTY,                    EMPTY,                    BLUE_,
            EMPTY,                                    EMPTY,                                    EMPTY
        ),
        // @formatter:on
        freePieces = Pair(5u, 5u), pieceToMove = false
    )

    @Test
    fun `loosing position3`() {
        TestCase.assertEquals(lostPosition3.evaluate(), Pair(-2000, 2000))
    }
}
