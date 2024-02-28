package com.example.mensmorris

import com.example.mensmorris.game.Position
import com.example.mensmorris.game.blue
import com.example.mensmorris.game.empty
import com.example.mensmorris.game.green
import com.example.mensmorris.game.occurredPositions
import com.example.mensmorris.game.resetAnalyze
import junit.framework.TestCase
import org.junit.Test

class CachingTest {
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

    @Test
    fun `cache test`() {
        position.solve(6u)
        TestCase.assertEquals(occurredPositions.size, 14427)
        TestCase.assertEquals(position.solve(4u).second, null)
        resetAnalyze()
        TestCase.assertEquals(position.solve(4u).second, null)
    }
}
