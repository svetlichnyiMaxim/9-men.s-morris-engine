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

    private val lostPosition2 = Position(
        mutableListOf(
            Blue(),                                     Green(),                                    Empty(),
                            Empty(),                    Empty(),                    Blue(),
                                            Green(),    Empty(),   Green(),
            Empty(),        Empty(),        Blue(),                Empty(),         Empty(),        Empty(),
                                            Blue(),    Empty(),    Empty(),
                            Empty(),                    Empty(),                    Blue(),
            Empty(),                                    Empty(),                                    Empty()
        ),
        freePieces = Pair(5u, 5u),
        pieceToMove = false
    )

    @Test
    fun `loosing position2`() {
        println(lostPosition2.evaluate())
    }

    private val lostPosition3 = Position(
        mutableListOf(
            Blue(),                                     Green(),                                    Empty(),
                            Empty(),                    Empty(),                    Empty(),
                                            Green(),    Blue(),   Green(),
            Empty(),        Empty(),        Blue(),                Empty(),         Empty(),        Empty(),
                                            Blue(),    Empty(),    Empty(),
                            Empty(),                    Empty(),                    Blue(),
            Empty(),                                    Empty(),                                    Empty()
        ),
        freePieces = Pair(5u, 5u),
        pieceToMove = false
    )

    @Test
    fun `loosing position3`() {
        println(lostPosition3.evaluate())
    }
}