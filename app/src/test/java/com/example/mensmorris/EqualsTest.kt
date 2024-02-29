package com.example.mensmorris

import com.example.mensmorris.game.BLUE_
import com.example.mensmorris.game.Position
import com.example.mensmorris.game.EMPTY
import com.example.mensmorris.game.GREEN
import junit.framework.TestCase
import org.junit.Test

class EqualsTest {
    private val position = Position(
        // @formatter:off
        mutableListOf(
            BLUE_,                             BLUE_,                                 EMPTY,
                        GREEN,                EMPTY,                BLUE_,
                                    EMPTY,    EMPTY,    EMPTY,
            EMPTY,    GREEN,    EMPTY,                EMPTY,    GREEN,        GREEN,
                                    EMPTY,    EMPTY,    EMPTY,
                        EMPTY,                EMPTY,                EMPTY,
            EMPTY,                            EMPTY,                                BLUE_
        ),
        // @formatter:on
        pieceToMove = true
    )

    /**
     * this tests we our copy method actually works
     */
    @Test
    fun `equals test`() {
        val position1 = position.copy()
        TestCase.assertEquals(position, position1)
    }

    private val position2 = Position(
        // @formatter:off
        mutableListOf(
            BLUE_,                             BLUE_,                                 EMPTY,
            GREEN,                EMPTY,                BLUE_,
            EMPTY,    EMPTY,    EMPTY,
            EMPTY,    GREEN,    EMPTY,                EMPTY,    GREEN,        GREEN,
            EMPTY,    EMPTY,    EMPTY,
            EMPTY,                EMPTY,                EMPTY,
            EMPTY,                            EMPTY,                                BLUE_
        ),
        // @formatter:on
        pieceToMove = true
    )

    /**
     * this tests we our copy method actually works
     */
    @Test
    fun `not equals test`() {
        TestCase.assertEquals(position, position2)
    }
}
