package com.example.mensmorris

import com.example.mensmorris.game.Blue
import com.example.mensmorris.game.Empty
import com.example.mensmorris.game.Green
import com.example.mensmorris.game.Position
import org.junit.Test

class DepthTest {
    private val wonPosition1 = Position(
        mutableListOf(
            Empty(),                                    Empty(),                                    Blue(),
                            Empty(),                    Empty(),                    Empty(),
                                        Empty(),        Empty(),        Blue(),
            Empty(),        Empty(),    Green(),                        Empty(),    Empty(),        Empty(),
                                        Green(),        Empty(),        Empty(),
                            Empty(),                    Empty(),                    Empty(),
            Empty(),                                    Empty(),                                    Green()
        ),
        freePieces = Pair(6u, 6u),
        pieceToMove = true
    )

    @Test
    fun `winning position1`() {
            wonPosition1.solve(5u).let {
                println(it.first)
                it.second.forEach {
                    it.display()
                }
            }
    }
}