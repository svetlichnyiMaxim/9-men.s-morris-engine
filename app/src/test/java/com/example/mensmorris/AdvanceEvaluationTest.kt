package com.example.mensmorris

import com.example.mensmorris.game.Blue
import com.example.mensmorris.game.Empty
import com.example.mensmorris.game.Green
import com.example.mensmorris.game.Position
import junit.framework.TestCase
import org.junit.Test

class AdvanceEvaluationTest {
    private val wonPosition = Position(
        mutableListOf(
            Blue(),                                     Empty(),                                     Empty(),
                            Empty(),                    Empty(),                    Empty(),
                                        Green(),        Empty(),        Green(),
            Empty(),        Empty(),    Blue(),                        Empty(),    Empty(),        Empty(),
                                        Blue(),        Empty(),        Empty(),
                            Empty(),                    Empty(),                    Empty(),
            Empty(),                                    Empty(),                                    Empty()
        ),
        freePieces = Pair(7u, 6u),
        pieceToMove = true
    )

    @Test
    fun `winning position`() {
        println(wonPosition.evaluate())
    }
    private val wonPosition1 = Position(
        mutableListOf(
            Empty(),                                    Empty(),                                     Empty(),
                            Empty(),                    Empty(),                    Empty(),
                                        Green(),        Blue(),         Green(),
            Empty(),        Empty(),    Blue(),                         Empty(),    Empty(),        Empty(),
                                        Blue(),         Empty(),        Empty(),
                            Empty(),                    Empty(),                    Empty(),
            Empty(),                                    Empty(),                                    Empty()
        ),
        freePieces = Pair(7u, 6u),
        pieceToMove = true
    )

    @Test
    fun `winning position1`() {
        println(wonPosition1.evaluate())
    }
/*
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
    }*/
}