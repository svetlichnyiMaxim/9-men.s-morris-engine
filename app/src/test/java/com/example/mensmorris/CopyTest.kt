package com.example.mensmorris

import com.example.mensmorris.game.Blue
import com.example.mensmorris.game.Empty
import com.example.mensmorris.game.Green
import com.example.mensmorris.game.Position
import com.example.mensmorris.game.occurredPositions
import com.example.mensmorris.game.resetAnalyze
import org.junit.Test

class CopyTest {
    private val position1 = mutableListOf(
            Blue(),                             Blue(),                                 Empty(),
                        Green(),                Empty(),                Blue(),
                                    Empty(),    Empty(),    Empty(),
            Empty(),    Green(),    Empty(),                Empty(),    Empty(),        Green(),
                                    Empty(),    Empty(),    Empty(),
                        Empty(),                Empty(),                Empty(),
            Empty(),                            Empty(),                                Blue()
        )
    @Test
    fun `copy test`() {
        val position2 = position1.map { it.copy() }
        position2[0].isGreen = null
        assert(position1 != position2)
        resetAnalyze()
    }
}