package com.example.mensmorris

import com.example.mensmorris.game.Blue
import com.example.mensmorris.game.Empty
import com.example.mensmorris.game.Green
import com.example.mensmorris.game.Position
import org.junit.Test
import kotlin.math.max

class MoveGenerationTest {
    private val position1 = Position(
        mutableListOf(
            Blue(),                                     Blue(),                                     Empty(),
                            Green(),                    Empty(),                    Empty(),
                                        Empty(),        Empty(),        Empty(),
            Empty(),        Green(),    Empty(),                        Empty(),    Empty(),        Empty(),
                                        Empty(),        Empty(),        Empty(),
                            Empty(),                    Empty(),                    Empty(),
            Empty(),                                    Empty(),                                    Green()
        ),
        freePieces = Pair(1u, 1u),
        pieceToMove = true,
        removalCount = 0u
    )

    @Test
    fun `winning test1`() {
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
            Blue(),                                     Empty(),                                    Empty(),
                            Empty(),                    Empty(),                    Empty(),
                                        Green(),        Empty(),        Green(),
            Empty(),        Empty(),    Blue(),                         Empty(),    Empty(),        Empty(),
                                        Blue(),         Empty(),        Empty(),
                            Empty(),                    Empty(),                    Empty(),
            Empty(),                                    Empty(),                                    Empty()
        ),
        freePieces = Pair(6u, 6u),
        pieceToMove = true,
        removalCount = 0u
    )

    @Test
    fun `winning test2`() {
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
            Blue(),                                     Empty(),                                    Empty(),
                            Empty(),                    Empty(),                    Empty(),
                                        Green(),        Green(),        Green(),
            Empty(),        Empty(),    Blue(),                         Empty(),    Empty(),        Empty(),
                                        Blue(),         Empty(),        Empty(),
                            Empty(),                    Empty(),                    Empty(),
            Empty(),                                    Empty(),                                    Empty()
        ),
        freePieces = Pair(5u, 6u),
        pieceToMove = true,
        removalCount = 1u
    )

    @Test
    fun `winning test3`() {
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
            Empty(),                                     Empty(),                                    Empty(),
                            Empty(),                    Empty(),                    Empty(),
                                        Green(),        Empty(),        Green(),
            Empty(),        Empty(),    Blue(),                         Empty(),    Empty(),        Empty(),
                                        Blue(),         Empty(),        Empty(),
                            Empty(),                    Empty(),                    Blue(),
            Empty(),                                    Empty(),                                    Empty()
        ),
        freePieces = Pair(5u, 5u),
        pieceToMove = false,
        removalCount = 0u
    )

    @Test
    fun `winning test4`() {
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