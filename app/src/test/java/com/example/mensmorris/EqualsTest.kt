package com.example.mensmorris

import com.example.mensmorris.game.Position
import com.example.mensmorris.game.blue
import com.example.mensmorris.game.empty
import com.example.mensmorris.game.green
import junit.framework.TestCase
import org.junit.Test

class EqualsTest {
    private val position = Position(
        // @formatter:off
        mutableListOf(
            blue(),                             blue(),                                 empty(),
                        green(),                empty(),                blue(),
                                    empty(),    empty(),    empty(),
            empty(),    green(),    empty(),                empty(),    green(),        green(),
                                    empty(),    empty(),    empty(),
                        empty(),                empty(),                empty(),
            empty(),                            empty(),                                blue()
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
            blue(),                             blue(),                                 empty(),
            green(),                empty(),                blue(),
            empty(),    empty(),    empty(),
            empty(),    green(),    empty(),                empty(),    green(),        green(),
            empty(),    empty(),    empty(),
            empty(),                empty(),                empty(),
            empty(),                            empty(),                                blue()
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
