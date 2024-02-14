package com.example.mensmorris

import com.example.mensmorris.game.Position
import com.example.mensmorris.game.blue
import com.example.mensmorris.game.empty
import com.example.mensmorris.game.green
import junit.framework.TestCase
import org.junit.Test

class AdvanceEvaluationTest {
    private val wonPosition = Position(
        // @formatter:off
        mutableListOf(
            blue(),                                     empty(),                                     empty(),
                            empty(),                    empty(),                    empty(),
                                        green(),        empty(),        green(),
            empty(),        empty(),    blue(),                        empty(),    empty(),        empty(),
                                        blue(),        empty(),        empty(),
                            empty(),                    empty(),                    empty(),
            empty(),                                    empty(),                                    empty()
        ),
        // @formatter:on
        freePieces = Pair(7u, 6u), pieceToMove = true
    )

    @Test
    fun `winning position`() {
        TestCase.assertEquals(wonPosition.evaluate(), Pair(195, -595))
    }

    private val wonPosition1 = Position(
        // @formatter:off
        mutableListOf(
            empty(),                                    empty(),                                     empty(),
                            empty(),                    empty(),                    empty(),
                                        green(),        blue(),         green(),
            empty(),        empty(),    blue(),                         empty(),    empty(),        empty(),
                                        blue(),         empty(),        empty(),
                            empty(),                    empty(),                    empty(),
            empty(),                                    empty(),                                    empty()
        ),
        // @formatter:on
        freePieces = Pair(7u, 6u), pieceToMove = true
    )

    @Test
    fun `winning position1`() {
        TestCase.assertEquals(wonPosition1.evaluate(), Pair(0, 0))
    }

    private val lostPosition2 = Position(
        // @formatter:off
        mutableListOf(
            blue(),                                     green(),                                    empty(),
                            empty(),                    empty(),                    blue(),
                                            green(),    empty(),   green(),
            empty(),        empty(),        blue(),                empty(),         empty(),        empty(),
                                            blue(),    empty(),    empty(),
                            empty(),                    empty(),                    blue(),
            empty(),                                    empty(),                                    empty()
        ),
        // @formatter:on
        freePieces = Pair(5u, 5u), pieceToMove = false
    )

    @Test
    fun `loosing position2`() {
        TestCase.assertEquals(lostPosition2.evaluate(), Pair(-2405, 1605))
    }

    private val lostPosition3 = Position(
        // @formatter:off
        mutableListOf(
            blue(),                                     green(),                                    empty(),
                            empty(),                    empty(),                    empty(),
                                            green(),    blue(),   green(),
            empty(),        empty(),        blue(),                empty(),         empty(),        empty(),
                                            blue(),    empty(),    empty(),
                            empty(),                    empty(),                    blue(),
            empty(),                                    empty(),                                    empty()
        ),
        // @formatter:on
        freePieces = Pair(5u, 5u), pieceToMove = false
    )

    @Test
    fun `loosing position3`() {
        TestCase.assertEquals(lostPosition3.evaluate(), Pair(-2000, 2000))
    }
}
