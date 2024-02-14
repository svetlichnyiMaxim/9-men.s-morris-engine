package com.example.mensmorris

import com.example.mensmorris.game.Position
import com.example.mensmorris.game.blue
import com.example.mensmorris.game.empty
import com.example.mensmorris.game.green
import com.example.mensmorris.game.occurredPositions
import junit.framework.TestCase
import org.junit.Test

class StrategyTests {
    @Test
    fun `winning test1`() {
        val position = Position(
            // @formatter:off
            mutableListOf(
                blue(),                                     empty(),                                     empty(),
                green(),                    empty(),                    empty(),
                empty(),        empty(),        empty(),
                empty(),        green(),    empty(),                        empty(),    empty(),        empty(),
                empty(),        empty(),        empty(),
                empty(),                    empty(),                    blue(),
                empty(),                                    empty(),                                    empty()
            ),
            // @formatter:on
            freePieces = Pair(1u, 1u), pieceToMove = true, removalCount = 0u
        )
        val correctResult = Pair(
            Pair(2147483647, -2147483648), mutableListOf(
                Position(
                    mutableListOf(
                        empty(),
                        empty(),
                        empty(),
                        green(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        green(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        green(),
                        empty(),
                        blue(),
                        empty(),
                        empty(),
                        empty()
                    ), freePieces = Pair(0u, 1u), pieceToMove = false, removalCount = 0u
                ), Position(
                    mutableListOf(
                        blue(),
                        empty(),
                        empty(),
                        green(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        green(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        green(),
                        empty(),
                        blue(),
                        empty(),
                        empty(),
                        empty()
                    ), freePieces = Pair(0u, 1u), pieceToMove = true, removalCount = 1u
                ), Position(
                    mutableListOf(
                        blue(),
                        empty(),
                        empty(),
                        green(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        green(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        blue(),
                        empty(),
                        empty(),
                        empty()
                    ), freePieces = Pair(1u, 1u), pieceToMove = true, removalCount = 0u
                )
            )
        )
        val ourResult = position.solve(2u)
        TestCase.assertEquals(ourResult, correctResult)
        occurredPositions.clear()
    }

    @Test
    fun `winning test2`() {
        val position = Position(
            // @formatter:off
            mutableListOf(
                blue(),                                     empty(),                                     empty(),
                                green(),                    empty(),                    empty(),
                                            empty(),        empty(),        empty(),
                empty(),        green(),    empty(),                        empty(),    empty(),        empty(),
                                            empty(),        empty(),        empty(),
                                empty(),                    empty(),                    blue(),
                empty(),                                    empty(),                                    empty()
            ),
            // @formatter:on
            freePieces = Pair(1u, 1u), pieceToMove = true, removalCount = 0u
        )
        val correctResult = Pair(
            Pair(2147483647, -2147483648), mutableListOf(
                Position(
                    mutableListOf(
                        empty(),
                        empty(),
                        empty(),
                        green(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        green(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        green(),
                        empty(),
                        blue(),
                        empty(),
                        empty(),
                        empty()
                    ), freePieces = Pair(0u, 1u), pieceToMove = false, removalCount = 0u
                ),
                Position(
                    mutableListOf(
                        blue(),
                        empty(),
                        empty(),
                        green(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        green(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        green(),
                        empty(),
                        blue(),
                        empty(),
                        empty(),
                        empty()
                    ), freePieces = Pair(0u, 1u), pieceToMove = true, removalCount = 1u
                ),
                Position(
                    mutableListOf(
                        blue(),
                        empty(),
                        empty(),
                        green(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        green(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        blue(),
                        empty(),
                        empty(),
                        empty()
                    ), freePieces = Pair(1u, 1u), pieceToMove = true, removalCount = 0u
                ),
            )
        )
        val ourResult = position.solve(2u)
        ourResult.second.onEach { it.display2() }
        TestCase.assertEquals(ourResult.first, correctResult.first)
        TestCase.assertEquals(ourResult.second.size, correctResult.second.size)
        for (i in 0..<ourResult.second.size) {
            TestCase.assertEquals(ourResult.second[i], correctResult.second[i])
        }
        occurredPositions.clear()
    }

    @Test
    fun `winning test3`() {
        val position = Position(
            // @formatter:off
            mutableListOf(
                empty(),                                empty(),                                    empty(),
                            empty(),                    empty(),                    empty(),
                                        green(),        empty(),        green(),
                empty(),    empty(),    blue(),                         empty(),    empty(),        empty(),
                                        blue(),         empty(),        empty(),
                            empty(),                    empty(),                    blue(),
                empty(),                                empty(),                                    empty()
            ),
            // @formatter:on
            freePieces = Pair(5u, 5u), pieceToMove = false, removalCount = 0u
        )
        val correctResult = Pair(
            Pair(-1000, 1000), mutableListOf(

                Position(
                    mutableListOf(
                        green(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        green(),
                        blue(),
                        green(),
                        empty(),
                        empty(),
                        blue(),
                        empty(),
                        empty(),
                        empty(),
                        blue(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        blue(),
                        empty(),
                        empty(),
                        empty()
                    ), freePieces = Pair(4u, 4u), pieceToMove = false, removalCount = 0u
                ),
                Position(
                    mutableListOf(
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        green(),
                        blue(),
                        green(),
                        empty(),
                        empty(),
                        blue(),
                        empty(),
                        empty(),
                        empty(),
                        blue(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        blue(),
                        empty(),
                        empty(),
                        empty()
                    ), freePieces = Pair(5u, 4u), pieceToMove = true, removalCount = 0u
                ),
                Position(
                    mutableListOf(
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        green(),
                        empty(),
                        green(),
                        empty(),
                        empty(),
                        blue(),
                        empty(),
                        empty(),
                        empty(),
                        blue(),
                        empty(),
                        empty(),
                        empty(),
                        empty(),
                        blue(),
                        empty(),
                        empty(),
                        empty()
                    ), freePieces = Pair(5u, 5u), pieceToMove = false, removalCount = 0u
                ),
            )
        )
        val ourResult = position.solve(2u)
        TestCase.assertEquals(ourResult, correctResult)
        occurredPositions.clear()
    }
}
