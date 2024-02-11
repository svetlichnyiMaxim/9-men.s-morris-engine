package com.example.mensmorris

import com.example.mensmorris.game.blue
import com.example.mensmorris.game.empty
import com.example.mensmorris.game.green
import com.example.mensmorris.game.Position
import org.junit.Test

class AdvanceEvaluationTest {
    private val wonPosition = Position(
        mutableListOf(
            blue(),                                     empty(),                                     empty(),
                            empty(),                    empty(),                    empty(),
                                        green(),        empty(),        green(),
            empty(),        empty(),    blue(),                        empty(),    empty(),        empty(),
                                        blue(),        empty(),        empty(),
                            empty(),                    empty(),                    empty(),
            empty(),                                    empty(),                                    empty()
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
            empty(),                                    empty(),                                     empty(),
                            empty(),                    empty(),                    empty(),
                                        green(),        blue(),         green(),
            empty(),        empty(),    blue(),                         empty(),    empty(),        empty(),
                                        blue(),         empty(),        empty(),
                            empty(),                    empty(),                    empty(),
            empty(),                                    empty(),                                    empty()
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
            blue(),                                     green(),                                    empty(),
                            empty(),                    empty(),                    blue(),
                                            green(),    empty(),   green(),
            empty(),        empty(),        blue(),                empty(),         empty(),        empty(),
                                            blue(),    empty(),    empty(),
                            empty(),                    empty(),                    blue(),
            empty(),                                    empty(),                                    empty()
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
            blue(),                                     green(),                                    empty(),
                            empty(),                    empty(),                    empty(),
                                            green(),    blue(),   green(),
            empty(),        empty(),        blue(),                empty(),         empty(),        empty(),
                                            blue(),    empty(),    empty(),
                            empty(),                    empty(),                    blue(),
            empty(),                                    empty(),                                    empty()
        ),
        freePieces = Pair(5u, 5u),
        pieceToMove = false
    )

    @Test
    fun `loosing position3`() {
        println(lostPosition3.evaluate())
    }
}
