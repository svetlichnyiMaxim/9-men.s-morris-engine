package com.example.mensmorris

import com.example.mensmorris.game.blue
import com.example.mensmorris.game.empty
import com.example.mensmorris.game.green
import com.example.mensmorris.game.Position
import org.junit.Test
import kotlin.math.max

class MoveGenerationTest {
    private val position1 = Position(
        mutableListOf(
            blue(),                                     blue(),                                     empty(),
                            green(),                    empty(),                    empty(),
                                        empty(),        empty(),        empty(),
            empty(),        green(),    empty(),                        empty(),    empty(),        empty(),
                                        empty(),        empty(),        empty(),
                            empty(),                    empty(),                    empty(),
            empty(),                                    empty(),                                    green()
        ),
        freePieces = Pair(1u, 1u),
        pieceToMove = true,
        removalCount = 0u
    )

    @Test
    fun `generation 1`() {
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
    }

    private val position2 = Position(
        mutableListOf(
            blue(),                                     empty(),                                    empty(),
                            empty(),                    empty(),                    empty(),
                                        green(),        empty(),        green(),
            empty(),        empty(),    blue(),                         empty(),    empty(),        empty(),
                                        blue(),         empty(),        empty(),
                            empty(),                    empty(),                    empty(),
            empty(),                                    empty(),                                    empty()
        ),
        freePieces = Pair(6u, 6u),
        pieceToMove = true,
        removalCount = 0u
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
    }

    private val position3 = Position(
        mutableListOf(
            blue(),                                     empty(),                                    empty(),
                            empty(),                    empty(),                    empty(),
                                        green(),        green(),        green(),
            empty(),        empty(),    blue(),                         empty(),    empty(),        empty(),
                                        blue(),         empty(),        empty(),
                            empty(),                    empty(),                    empty(),
            empty(),                                    empty(),                                    empty()
        ),
        freePieces = Pair(5u, 6u),
        pieceToMove = true,
        removalCount = 1u
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
    }
    private val position4 = Position(
        mutableListOf(
            empty(),                                     empty(),                                    empty(),
                            empty(),                    empty(),                    empty(),
                                        green(),        empty(),        green(),
            empty(),        empty(),    blue(),                         empty(),    empty(),        empty(),
                                        blue(),         empty(),        empty(),
                            empty(),                    empty(),                    blue(),
            empty(),                                    empty(),                                    empty()
        ),
        freePieces = Pair(5u, 5u),
        pieceToMove = false,
        removalCount = 0u
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
    }
}
