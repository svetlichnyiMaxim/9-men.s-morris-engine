package com.example.mensmorris

import com.example.mensmorris.game.Piece
import com.example.mensmorris.game.Position
import junit.framework.TestCase.assertEquals
import org.junit.Test

@Suppress("unused")
class MainTest {
    private val wonPosition = Position(
        mutableListOf(
            Piece.BLUE_,                                    Piece.BLUE_,                                    Piece.BLUE_,
                            Piece.GREEN,                    Piece.EMPTY,                    Piece.EMPTY,
                                            Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,
            Piece.EMPTY,    Piece.GREEN,    Piece.EMPTY,                    Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,
                                            Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,
                            Piece.EMPTY,                    Piece.EMPTY,                    Piece.EMPTY,
            Piece.EMPTY,                                    Piece.EMPTY,                                    Piece.EMPTY
        ),
        pieceToMove = Piece.GREEN,
        removalCount = 0u
    )

    @Test
    fun `winning position`() {
        assertEquals(wonPosition.evaluate(), Pair((-128).toByte(), 127.toByte()))
    }

    private val lostPosition = Position(
        mutableListOf(
            Piece.BLUE_,                                    Piece.BLUE_,                                    Piece.BLUE_,
            Piece.GREEN,                    Piece.EMPTY,                    Piece.EMPTY,
            Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,
            Piece.EMPTY,    Piece.GREEN,    Piece.EMPTY,                    Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,
            Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,
            Piece.EMPTY,                    Piece.EMPTY,                    Piece.EMPTY,
            Piece.EMPTY,                                    Piece.EMPTY,                                    Piece.EMPTY
        ),
        pieceToMove = Piece.GREEN,
        removalCount = 0u
    )

    @Test
    fun `loosing position`() {
        assertEquals(wonPosition.evaluate(), Pair((-128).toByte(), 127.toByte()))
    }

    private val evaluationPosition = Position(
        mutableListOf(
            Piece.BLUE_,                                    Piece.EMPTY,                                    Piece.BLUE_,
            Piece.GREEN,                    Piece.EMPTY,                    Piece.EMPTY,
            Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,
            Piece.EMPTY,    Piece.GREEN,    Piece.EMPTY,                    Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,
            Piece.EMPTY,    Piece.GREEN,    Piece.EMPTY,
                            Piece.EMPTY,                    Piece.BLUE_,                    Piece.EMPTY,
            Piece.EMPTY,                                    Piece.EMPTY,                                    Piece.BLUE_
        ),
        pieceToMove = Piece.GREEN,
        removalCount = 0u
    )

    @Test
    fun `evaluation test`() {
        assertEquals(evaluationPosition.findPossibleTriple(),  Pair(2.toUByte(), 0.toUByte()))
    }
}
