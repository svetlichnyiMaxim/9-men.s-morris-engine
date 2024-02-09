package com.example.mensmorris

import com.example.mensmorris.game.Blue
import com.example.mensmorris.game.Empty
import com.example.mensmorris.game.Green
import com.example.mensmorris.game.Position
import com.example.mensmorris.game.occurredPositions
import com.example.mensmorris.game.resetAnalyze
import com.example.mensmorris.game.resetCachedPositions
import junit.framework.TestCase
import org.junit.Test

class CachingTest {
    private val position = Position(mutableListOf(
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
    fun `cache`() {
        position.solve(6u)
        TestCase.assertEquals(occurredPositions.size == 8)
        TestCase.assertEquals(position.solve(4u).second.size == 1)
        resetAnalyze()
        TestCase.assertEquals(position.solve(4u).second.size == 1)
    }
}