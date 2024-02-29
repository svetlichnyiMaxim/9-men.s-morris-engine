package com.example.mensmorris

import com.example.mensmorris.game.BLUE_
import com.example.mensmorris.game.Position
import com.example.mensmorris.game.EMPTY
import com.example.mensmorris.game.GREEN
import com.example.mensmorris.game.occurredPositions
import com.example.mensmorris.game.resetAnalyze
import junit.framework.TestCase
import org.junit.Test

class CachingTest {
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

    @Test
    fun `cache test`() {
        position.solve(6u)
        TestCase.assertEquals(occurredPositions.size, 3028)
        TestCase.assertEquals(position.solve(4u).second, null)
        resetAnalyze()
        TestCase.assertEquals(position.solve(4u).second, null)
    }
}
