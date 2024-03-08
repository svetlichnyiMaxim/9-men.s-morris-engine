package com.example.mensmorris

import com.example.mensmorris.game.EMPTY
import com.example.mensmorris.game.Position
import com.example.mensmorris.game.occurredPositions
import org.junit.Test
import kotlin.system.measureTimeMillis

class BenchmarkTest {
    private val wonPosition2 = Position(
        // @formatter:off
        arrayOf(
            EMPTY,                  EMPTY,                  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  EMPTY,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        freePieces = Pair(5u, 5u), pieceToMove = true
    )

    @Test
    fun benchmark() {
        // we use this to initialize everything we will need
        // to make time measurements closer to the reality
        wonPosition2.solve(3u)
        occurredPositions.clear()

        repeat(10) {
            val time = measureTimeMillis {
                wonPosition2.solve(6u)
            }
            println(time)
            occurredPositions.clear()
        }
    }

    /*
11237
10122
10109
10090
10064
10096
10058
10228
10320
10110
     */
}