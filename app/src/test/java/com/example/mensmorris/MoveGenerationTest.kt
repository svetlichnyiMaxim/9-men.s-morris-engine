package com.example.mensmorris

import com.example.mensmorris.game.BLUE_
import com.example.mensmorris.game.Position
import com.example.mensmorris.game.EMPTY
import com.example.mensmorris.game.GREEN
import com.example.mensmorris.game.occurredPositions
import com.example.mensmorris.positions.position1
import org.junit.Test
import kotlin.math.max

class MoveGenerationTest {
    private val position11 = Position(
        // @formatter:off
        mutableListOf(
            BLUE_,                  BLUE_,                  EMPTY,
                    GREEN,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  GREEN
        ),
        // @formatter:on
        freePieces = Pair(1u, 1u), pieceToMove = true, removalCount = 0u
    )

    @Test
    fun `generation 1`() {
        var maxEvaluationGreen = Int.MIN_VALUE
        var maxEvaluationBlue = Int.MIN_VALUE
        position11.generatePositions(1u).forEach { it2 ->
            println(it2.freePieces)
            maxEvaluationGreen = max(maxEvaluationGreen, it2.evaluate().first)
            maxEvaluationBlue = max(maxEvaluationBlue, it2.evaluate().second)
            println(it2.evaluate())
            println(it2.removalCount)
            it2.display()
        }
        println("green - $maxEvaluationGreen; blue - $maxEvaluationBlue")
        occurredPositions.clear()
    }

    private val position2 = Position(
        // @formatter:off
        mutableListOf(
            BLUE_,                  EMPTY,                  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
                            GREEN,  EMPTY,  GREEN,
            EMPTY,  EMPTY,  BLUE_,          EMPTY,  EMPTY,  EMPTY,
                            BLUE_,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        freePieces = Pair(6u, 6u), pieceToMove = true, removalCount = 0u
    )

    @Test
    fun `generation 2`() {
        var maxEvaluationGreen = Int.MIN_VALUE
        var maxEvaluationBlue = Int.MIN_VALUE
        position2.generatePositions(1u).forEach { it2 ->
            println(it2.freePieces)
            maxEvaluationGreen = max(maxEvaluationGreen, it2.evaluate().first)
            maxEvaluationBlue = max(maxEvaluationBlue, it2.evaluate().second)
            println(it2.evaluate())
            println(it2.removalCount)
            it2.display()
        }
        println("green - $maxEvaluationGreen; blue - $maxEvaluationBlue")
        occurredPositions.clear()
    }

    private val position3 = Position(
        // @formatter:off
        mutableListOf(
            BLUE_,                  EMPTY,                  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
                            GREEN,  GREEN,  GREEN,
            EMPTY,  EMPTY,  BLUE_,          EMPTY,  EMPTY,  EMPTY,
                            BLUE_,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        freePieces = Pair(5u, 6u), pieceToMove = true, removalCount = 1u
    )

    @Test
    fun `generation 3`() {
        var maxEvaluationGreen = Int.MIN_VALUE
        var maxEvaluationBlue = Int.MIN_VALUE
        position3.generatePositions(1u).forEach { it2 ->
            println(it2.freePieces)
            maxEvaluationGreen = max(maxEvaluationGreen, it2.evaluate().first)
            maxEvaluationBlue = max(maxEvaluationBlue, it2.evaluate().second)
            println(it2.evaluate())
            println(it2.removalCount)
            it2.display()
        }
        println("green - $maxEvaluationGreen; blue - $maxEvaluationBlue")
        occurredPositions.clear()
    }

    private val position4 = Position(
        // @formatter:off
        mutableListOf(
            EMPTY,                  EMPTY,                  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
                            GREEN,  EMPTY,  GREEN,
            EMPTY,  EMPTY,  BLUE_,          EMPTY,  EMPTY,  EMPTY,
                            BLUE_,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          BLUE_,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        freePieces = Pair(5u, 5u), pieceToMove = false, removalCount = 0u
    )

    @Test
    fun `generation 4`() {
        var maxEvaluationGreen = Int.MIN_VALUE
        var maxEvaluationBlue = Int.MIN_VALUE
        position4.generatePositions(1u).forEach { it2 ->
            println(it2.freePieces)
            maxEvaluationGreen = max(maxEvaluationGreen, it2.evaluate().first)
            maxEvaluationBlue = max(maxEvaluationBlue, it2.evaluate().second)
            println(it2.evaluate())
            println(it2.removalCount)
            it2.display()
        }
        println("green - $maxEvaluationGreen; blue - $maxEvaluationBlue")
        occurredPositions.clear()
    }

    @Test
    fun `generate 5`() {
        var maxEvaluationGreen = Int.MIN_VALUE
        var maxEvaluationBlue = Int.MIN_VALUE
        position1.generatePositions(1u).forEach { it2 ->
            println(it2.freePieces)
            maxEvaluationGreen = max(maxEvaluationGreen, it2.evaluate().first)
            maxEvaluationBlue = max(maxEvaluationBlue, it2.evaluate().second)
            println(it2.evaluate())
            println(it2.removalCount)
            it2.display()
        }
        println("green - $maxEvaluationGreen; blue - $maxEvaluationBlue")
        occurredPositions.clear()
    }
}
