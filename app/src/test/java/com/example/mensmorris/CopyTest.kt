package com.example.mensmorris

import com.example.mensmorris.game.blue
import com.example.mensmorris.game.empty
import com.example.mensmorris.game.green
import com.example.mensmorris.game.resetAnalyze
import org.junit.Test

class CopyTest {
    private val position = mutableListOf(
            blue(),                             blue(),                                 empty(),
                        green(),                empty(),                blue(),
                                    empty(),    empty(),    empty(),
            empty(),    green(),    empty(),                empty(),    empty(),        green(),
                                    empty(),    empty(),    empty(),
                        empty(),                empty(),                empty(),
            empty(),                            empty(),                                blue()
        )

    /**
     * this tests we our copy method actually works
     */
    @Test
    fun `copy`() {
        val position2 = position.map { it.copy() }
        position2[0].isGreen = null
        assert(position != position2)
        resetAnalyze()
    }
}
