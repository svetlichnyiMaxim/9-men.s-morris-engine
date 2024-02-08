package com.example.mensmorris

import com.example.mensmorris.game.Blue
import com.example.mensmorris.game.Empty
import com.example.mensmorris.game.Green
import com.example.mensmorris.game.Position
import com.example.mensmorris.game.occurredPositions
import com.example.mensmorris.game.resetAnalyze
import com.example.mensmorris.game.resetCachedPositions
import org.junit.Test

class CachingTest {
    private val position1 = Position(mutableListOf(
            Blue(),                             Blue(),                                 Empty(),
                        Green(),                Empty(),                Blue(),
                                    Empty(),    Empty(),    Empty(),
            Empty(),    Green(),    Empty(),                Empty(),    Green(),        Green(),
                                    Empty(),    Empty(),    Empty(),
                        Empty(),                Empty(),                Empty(),
            Empty(),                            Empty(),                                Blue()
        ), pieceToMove = true
    )
    @Test
    fun `copy test`() {
        position1.solve(6u)
        assert(occurredPositions.size == 8)
        assert(position1.solve(4u).second.size == 1)
        resetAnalyze()
        assert(position1.solve(4u).second.size == 1)
    }
}