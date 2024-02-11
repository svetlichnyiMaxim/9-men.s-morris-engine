package com.example.mensmorris

import com.example.mensmorris.game.blue
import com.example.mensmorris.game.empty
import com.example.mensmorris.game.green
import com.example.mensmorris.game.Position
import junit.framework.TestCase
import org.junit.Test

class UnfinishedTriplesEvaluation {
    private val wonPosition = Position(
        mutableListOf(
            blue(),                                     blue(),                                     empty(),
                            green(),                    empty(),                    empty(),
                                        empty(),        empty(),        empty(),
            empty(),        green(),    empty(),                        empty(),    empty(),        empty(),
                                        empty(),        empty(),        empty(),
                            empty(),                    empty(),                    empty(),
            empty(),                                    empty(),                                    empty()
        ),
        pieceToMove = true
    )

    @Test
    fun `winning position`() {
        TestCase.assertEquals(wonPosition.unfinishedTriples(), Pair(1, 1))
    }
    private val wonPosition1 = Position(
        mutableListOf(
            blue(),                                     blue(),                                     empty(),
                            green(),                    empty(),                    empty(),
                                        empty(),        empty(),        empty(),
            empty(),        green(),    empty(),                        empty(),    empty(),        empty(),
                                        empty(),        empty(),        empty(),
                            empty(),                    empty(),                    empty(),
            empty(),                                    empty(),                                    empty()
        ),
        freePieces = Pair(0u, 1u),
        pieceToMove = true
    )

    @Test
    fun `winning position1`() {
        TestCase.assertEquals(wonPosition1.evaluate(), Pair(-2147483648, 2147483647))
    }

    private val lostPosition = Position(
        mutableListOf(
            blue(),                                     blue(),                                    blue(),
                            green(),                    empty(),                    empty(),
                                            empty(),    empty(),    empty(),
            empty(),        green(),        empty(),                empty(),        empty(),        empty(),
                                            empty(),    empty(),    empty(),
                            empty(),                    empty(),                    empty(),
            empty(),                                    empty(),                                    empty()
        ),
        pieceToMove = false
    )

    @Test
    fun `loosing position`() {
        TestCase.assertEquals(lostPosition.evaluate(), Pair(-2147483648, 2147483647))
    }
}
