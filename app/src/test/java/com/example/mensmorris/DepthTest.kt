package com.example.mensmorris

import com.example.mensmorris.game.BLUE_
import com.example.mensmorris.game.EMPTY
import com.example.mensmorris.game.GREEN
import com.example.mensmorris.game.Position
import org.junit.Test

class DepthTest {
    private val wonPosition = Position(
        // @formatter:off
        arrayOf(
            EMPTY,                  EMPTY,                  BLUE_,
                    EMPTY,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  BLUE_,
            EMPTY,  EMPTY,  GREEN,          EMPTY,  EMPTY,  EMPTY,
                            GREEN,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  GREEN
        ),
        // @formatter:on
        freePieces = Pair(6u, 6u),
        pieceToMove = true
    )

    @Test
    fun `depth test`() {
        wonPosition.solve(5u).let {
            var currentPos = wonPosition
            println(it.first)
            it.second!!.forEach { move ->
                currentPos = move.producePosition(currentPos)
                currentPos.display()
            }
        }
    }
}
