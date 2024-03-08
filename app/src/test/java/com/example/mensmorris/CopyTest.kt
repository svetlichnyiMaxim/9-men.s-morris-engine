package com.example.mensmorris

import com.example.mensmorris.game.BLUE_
import com.example.mensmorris.game.Position
import com.example.mensmorris.game.EMPTY
import com.example.mensmorris.game.GREEN
import com.example.mensmorris.game.resetAnalyze
import org.junit.Test

class CopyTest {
    private val position = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  BLUE_,                  EMPTY,
                    GREEN,          EMPTY,          BLUE_,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  GREEN,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  BLUE_
        ),
        // @formatter:on
        pieceToMove = true
    )

    /**
     * this tests we our copy method actually works
     */
    @Test
    fun `copy test`() {
        val position2 = position.copy()
        position2.positions[0] = null
        assert(position != position2)
        resetAnalyze()
    }
}
