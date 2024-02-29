package com.example.mensmorris

import com.example.mensmorris.game.BLUE_
import com.example.mensmorris.game.EMPTY
import com.example.mensmorris.game.GREEN
import com.example.mensmorris.game.Position
import junit.framework.TestCase
import org.junit.Test

class BasicEvaluationTest {
    private val wonPosition1 = Position(
        // @formatter:off
        mutableListOf(
            BLUE_,                                     BLUE_,                                     BLUE_,
                            GREEN,                    EMPTY,                    EMPTY,
                                        EMPTY,        EMPTY,        EMPTY,
            EMPTY,        GREEN,    EMPTY,                        EMPTY,    EMPTY,        EMPTY,
                                        EMPTY,        EMPTY,        EMPTY,
                            EMPTY,                    EMPTY,                    EMPTY,
            EMPTY,                                    EMPTY,                                    EMPTY
        ),
        // @formatter:on
        pieceToMove = true
    )

    @Test
    fun `win 1`() {
        TestCase.assertEquals(wonPosition1.evaluate(), Pair(-2146483648, 2147483647))
    }

    private val wonPosition2 = Position(
        // @formatter:off
        mutableListOf(
            BLUE_,                                    BLUE_,                                     EMPTY,
                            GREEN,                    EMPTY,                    EMPTY,
                                        EMPTY,        EMPTY,        EMPTY,
            EMPTY,          GREEN,      EMPTY,                        EMPTY,    EMPTY,        EMPTY,
                                        EMPTY,        EMPTY,        EMPTY,
                            EMPTY,                    EMPTY,                    EMPTY,
            EMPTY,                                    EMPTY,                                    EMPTY
        ),
        // @formatter:on
        freePieces = Pair(0u, 1u), pieceToMove = true
    )

    @Test
    fun `win 2`() {
        TestCase.assertEquals(wonPosition2.evaluate(), Pair(-2146483648, 2147483647))
    }

    private val lostPosition = Position(
        // @formatter:off
        mutableListOf(
            BLUE_,                                     BLUE_,                                    BLUE_,
                            GREEN,                    EMPTY,                    EMPTY,
                                            EMPTY,    EMPTY,    EMPTY,
            EMPTY,        GREEN,        EMPTY,                EMPTY,        EMPTY,        EMPTY,
                                            EMPTY,    EMPTY,    EMPTY,
                            EMPTY,                    EMPTY,                    EMPTY,
            EMPTY,                                    EMPTY,                                    EMPTY
        ),
        // @formatter:on
        pieceToMove = false
    )

    @Test
    fun `lose test`() {
        TestCase.assertEquals(lostPosition.evaluate(), Pair(-2146483648, 2147483647))
    }
}
