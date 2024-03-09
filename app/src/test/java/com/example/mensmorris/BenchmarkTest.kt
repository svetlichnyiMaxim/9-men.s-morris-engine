package com.example.mensmorris

import com.example.mensmorris.game.EMPTY
import com.example.mensmorris.game.Position
import com.example.mensmorris.game.occurredPositions
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.math.round
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
    fun idk() {
        runBlocking {
            val flow = (1..7).asFlow()
                .conflate()
                .map {
                    delay(round(1000L * 7.0 / it).toLong())
                    println("idk $it")
                }
            println("starting")
            flow.collect()
        }
    }

    @Test
    fun benchmark() {
        // we use this to initialize everything we will need
        // to make time measurements closer to the reality
        wonPosition2.solveBlocking(3u)
        occurredPositions.clear()

        var total = 0L
        val times = 10
        repeat(10) {
            val time = measureTimeMillis {
                wonPosition2.solveBlocking(6u)
            }
            total += time
            println(time)
            occurredPositions.clear()
        }
        println("average: ${total / times}")
    }

    /*
8066
6580
6493
6377
6069
6419
7005
6728
6481
6145
average: 6636
     */
}