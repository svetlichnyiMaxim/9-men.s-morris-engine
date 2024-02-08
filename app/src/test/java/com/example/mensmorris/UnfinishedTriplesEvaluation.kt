package com.example.mensmorris

import com.example.mensmorris.game.Blue
import com.example.mensmorris.game.Empty
import com.example.mensmorris.game.Green
import com.example.mensmorris.game.Position
import junit.framework.TestCase
import org.junit.Test

class UnfinishedTriplesEvaluation {
    private val wonPosition = Position(
        mutableListOf(
            Blue(),                                     Blue(),                                     Empty(),
                            Green(),                    Empty(),                    Empty(),
                                        Empty(),        Empty(),        Empty(),
            Empty(),        Green(),    Empty(),                        Empty(),    Empty(),        Empty(),
                                        Empty(),        Empty(),        Empty(),
                            Empty(),                    Empty(),                    Empty(),
            Empty(),                                    Empty(),                                    Empty()
        ),
        pieceToMove = true
    )

    @Test
    fun `winning position`() {
        TestCase.assertEquals(wonPosition.findUnfinishedTriples(), Pair(1, 1))
    }
    private val wonPosition1 = Position(
        mutableListOf(
            Blue(),                                     Blue(),                                     Empty(),
                            Green(),                    Empty(),                    Empty(),
                                        Empty(),        Empty(),        Empty(),
            Empty(),        Green(),    Empty(),                        Empty(),    Empty(),        Empty(),
                                        Empty(),        Empty(),        Empty(),
                            Empty(),                    Empty(),                    Empty(),
            Empty(),                                    Empty(),                                    Empty()
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
            Blue(),                                     Blue(),                                    Blue(),
                            Green(),                    Empty(),                    Empty(),
                                            Empty(),    Empty(),    Empty(),
            Empty(),        Green(),        Empty(),                Empty(),        Empty(),        Empty(),
                                            Empty(),    Empty(),    Empty(),
                            Empty(),                    Empty(),                    Empty(),
            Empty(),                                    Empty(),                                    Empty()
        ),
        pieceToMove = false
    )

    @Test
    fun `loosing position`() {
        TestCase.assertEquals(lostPosition.evaluate(), Pair(-2147483648, 2147483647))
    }
}