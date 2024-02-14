package com.example.mensmorris

import com.example.mensmorris.game.Position
import com.example.mensmorris.game.blue
import com.example.mensmorris.game.empty
import com.example.mensmorris.game.green
import com.example.mensmorris.game.resetAnalyze
import org.junit.Test

class CopyTest {
    private val position = Position(
        // @formatter:off
        mutableListOf(
            blue(),                             blue(),                                 empty(),
                        green(),                empty(),                blue(),
                                    empty(),    empty(),    empty(),
            empty(),    green(),    empty(),                empty(),    empty(),        green(),
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
    fun `copy test`() {
        val position2 = position.copy()
        position2.positions[0].isGreen = null
        assert(position != position2)
        resetAnalyze()
    }
}
