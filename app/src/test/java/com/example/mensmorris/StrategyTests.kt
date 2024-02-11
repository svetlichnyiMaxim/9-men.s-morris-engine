package com.example.mensmorris

import com.example.mensmorris.game.blue
import com.example.mensmorris.game.empty
import com.example.mensmorris.game.green
import com.example.mensmorris.game.Position
import org.junit.Test

class StrategyTests {
    private val position1 = Position(
        mutableListOf(
            blue(),                                     empty(),                                     empty(),
                            green(),                    empty(),                    empty(),
                                        empty(),        empty(),        empty(),
            empty(),        green(),    empty(),                        empty(),    empty(),        empty(),
                                        empty(),        empty(),        empty(),
                            empty(),                    empty(),                    blue(),
            empty(),                                    empty(),                                    empty()
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
            blue(),                                     empty(),                                     empty(),
                            green(),                    empty(),                    empty(),
                                        empty(),        empty(),        empty(),
            empty(),        green(),    empty(),                        empty(),    empty(),        empty(),
                                        empty(),        empty(),        empty(),
                            empty(),                    empty(),                    blue(),
            empty(),                                    empty(),                                    empty()
        ),
        freePieces = Pair(1u, 1u),
        pieceToMove = true,
        removalCount = 0u
    )

    @Test
    fun `winning test2`() {
        position2.solve(2u).let {
            println(it.first)
            it.second.forEach { it2 ->
                it2.display()
            }
        }
    }

    private val position3 = Position(
        mutableListOf(
            empty(),                                     empty(),                                     empty(),
                            empty(),                    empty(),                    empty(),
                                        green(),        empty(),        green(),
            empty(),        empty(),    blue(),                        empty(),    empty(),        empty(),
                                        blue(),        empty(),        empty(),
                            empty(),                    empty(),                    blue(),
            empty(),                                    empty(),                                    empty()
        ),
        freePieces = Pair(5u, 5u),
        pieceToMove = false,
        removalCount = 0u
    )

    @Test
    fun `winning test3`() {
        position3.solve(2u).let {
            println(it.first)
            it.second.forEach { it2 ->
                it2.display()
            }
        }
    }
}
