package com.example.mensmorris

import com.example.mensmorris.game.blue
import com.example.mensmorris.game.empty
import com.example.mensmorris.game.green
import com.example.mensmorris.game.Position
import org.junit.Test

class DepthTest {
    private val wonPosition = Position(
        // @formatter:off
        mutableListOf(
            empty(),                                    empty(),                                    blue(),
                            empty(),                    empty(),                    empty(),
                                        empty(),        empty(),        blue(),
            empty(),        empty(),    green(),                        empty(),    empty(),        empty(),
                                        green(),        empty(),        empty(),
                            empty(),                    empty(),                    empty(),
            empty(),                                    empty(),                                    green()
        ),
        // @formatter:on
        freePieces = Pair(6u, 6u),
        pieceToMove = true
    )

    @Test
    fun `depth test`() {
        wonPosition.solve(5u).let {
            println(it.first)
            it.second.forEach { pos ->
                pos.display()
            }
        }
    }
}
