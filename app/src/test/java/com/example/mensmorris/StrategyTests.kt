package com.example.mensmorris

import com.example.mensmorris.game.Blue
import com.example.mensmorris.game.Empty
import com.example.mensmorris.game.Green
import com.example.mensmorris.game.Position
import org.junit.Test

class StrategyTests {
    private val position1 = Position(
        mutableListOf(
            Blue(),                                     Empty(),                                     Empty(),
                            Green(),                    Empty(),                    Empty(),
                                        Empty(),        Empty(),        Empty(),
            Empty(),        Green(),    Empty(),                        Empty(),    Empty(),        Empty(),
                                        Empty(),        Empty(),        Empty(),
                            Empty(),                    Empty(),                    Blue(),
            Empty(),                                    Empty(),                                    Empty()
        ),
        freePieces = Pair(1u, 1u),
        pieceToMove = true,
        removalCount = 0u
    )

    @Test
    fun `winning test1`() {
        position1.solve(2u).let {
            println(it.first)
            it.second.forEach { it2 ->
                it2.display()
            }
        }
    }

    private val position2 = Position(
        mutableListOf(
            Blue(),                                     Empty(),                                     Empty(),
                            Green(),                    Empty(),                    Empty(),
                                        Empty(),        Empty(),        Empty(),
            Empty(),        Green(),    Empty(),                        Empty(),    Empty(),        Empty(),
                                        Empty(),        Empty(),        Empty(),
                            Empty(),                    Empty(),                    Blue(),
            Empty(),                                    Empty(),                                    Empty()
        ),
        freePieces = Pair(1u, 1u),
        pieceToMove = true,
        removalCount = 0u
    )

    @Test
    fun `winning test2`() {
        position1.solve(2u).let {
            println(it.first)
            it.second.forEach { it2 ->
                it2.display()
            }
        }
    }

    private val position3 = Position(
        mutableListOf(
            Empty(),                                     Empty(),                                     Empty(),
                            Empty(),                    Empty(),                    Empty(),
                                        Green(),        Empty(),        Green(),
            Empty(),        Empty(),    Blue(),                        Empty(),    Empty(),        Empty(),
                                        Blue(),        Empty(),        Empty(),
                            Empty(),                    Empty(),                    Blue(),
            Empty(),                                    Empty(),                                    Empty()
        ),
        freePieces = Pair(6u, 6u),
        pieceToMove = false,
        removalCount = 0u
    )

    @Test
    fun `winning test3`() {
        position3.solve(3u).let {
            println(it.first)
            it.second.forEach { it2 ->
                it2.display()
            }
        }
    }
}