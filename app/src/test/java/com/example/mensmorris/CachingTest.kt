package com.example.mensmorris

import com.example.mensmorris.game.blue
import com.example.mensmorris.game.empty
import com.example.mensmorris.game.green
import com.example.mensmorris.game.Position
import com.example.mensmorris.game.occurredPositions
import com.example.mensmorris.game.resetAnalyze
import junit.framework.TestCase
import org.junit.Test

class CachingTest {
    private val position = Position(mutableListOf(
            blue(),                             blue(),                                 empty(),
                        green(),                empty(),                blue(),
                                    empty(),    empty(),    empty(),
            empty(),    green(),    empty(),                empty(),    green(),        green(),
                                    empty(),    empty(),    empty(),
                        empty(),                empty(),                empty(),
            empty(),                            empty(),                                blue()
        ), pieceToMove = true
    )
    @Test
    fun `cache`() {
        position.solve(6u)
        TestCase.assertEquals(occurredPositions.size, 42)
        TestCase.assertEquals(position.solve(4u).second.size, 1)
        resetAnalyze()
        TestCase.assertEquals(position.solve(4u).second.size, 1)
    }
}
