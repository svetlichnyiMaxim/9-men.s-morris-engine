package com.example.mensmorris

import com.example.mensmorris.game.blue
import com.example.mensmorris.game.empty
import com.example.mensmorris.game.green
import com.example.mensmorris.game.Position
import junit.framework.TestCase
import org.junit.Test

class BasicEvaluationTest {
    private val wonPosition1 = Position(
        mutableListOf(
            blue(),                                     blue(),                                     blue(),
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
    fun `win 1`() {
        TestCase.assertEquals(wonPosition1.evaluate(), Pair(-2147483648, 2147483647))
    }
    private val wonPosition2 = Position(
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
    fun `win 2`() {
        TestCase.assertEquals(wonPosition2.evaluate(), Pair(-2147483648, 2147483647))
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
    fun `lose`() {
        TestCase.assertEquals(lostPosition.evaluate(), Pair(-2147483648, 2147483647))
    }
}
