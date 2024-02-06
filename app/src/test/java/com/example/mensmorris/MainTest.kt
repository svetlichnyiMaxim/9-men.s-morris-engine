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

    private val evaluationPosition1 = Position(
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
    fun `evaluation test1`() {
        assertEquals(evaluationPosition1.findPossibleTriple(),  Pair(2.toUByte(), 0.toUByte()))
    }

    private val evaluationPosition2 = Position(
        mutableListOf(
            Piece.EMPTY,                                    Piece.EMPTY,                                    Piece.GREEN,
                            Piece.EMPTY,                    Piece.EMPTY,                    Piece.BLUE_,
                                            Piece.EMPTY,    Piece.EMPTY,    Piece.GREEN,
            Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,                    Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,
                                            Piece.BLUE_,    Piece.EMPTY,    Piece.EMPTY,
                            Piece.GREEN,                    Piece.EMPTY,                    Piece.EMPTY,
            Piece.BLUE_,                                    Piece.EMPTY,                                    Piece.EMPTY
        ),
        pieceToMove = Piece.GREEN,
        removalCount = 0u
    )

    @Test
    fun `evaluation test2`() {
        assertEquals(evaluationPosition2.findPossibleTriple(),  Pair(0.toUByte(), 0.toUByte()))
    }

    private val evaluationPosition3 = Position(
        mutableListOf(
            Piece.EMPTY,                                    Piece.EMPTY,                                    Piece.GREEN,
                            Piece.EMPTY,                    Piece.EMPTY,                    Piece.BLUE_,
                                            Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,
            Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,                    Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,
                                            Piece.BLUE_,    Piece.EMPTY,    Piece.EMPTY,
                            Piece.GREEN,                    Piece.EMPTY,                    Piece.EMPTY,
            Piece.BLUE_,                                    Piece.EMPTY,                                    Piece.GREEN
        ),
        pieceToMove = Piece.GREEN,
        removalCount = 0u
    )

    @Test
    fun `evaluation test3`() {
        assertEquals(evaluationPosition3.evaluate(),  Pair(30.toByte(), 0.toByte()))
    }

    private val evaluationPosition4 = Position(
        mutableListOf(
            Piece.GREEN,                                    Piece.GREEN,                                    Piece.EMPTY,
                            Piece.EMPTY,                    Piece.EMPTY,                    Piece.EMPTY,
                                            Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,
            Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,                    Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,
                                            Piece.BLUE_,    Piece.BLUE_,    Piece.BLUE_,
                            Piece.EMPTY,                    Piece.EMPTY,                    Piece.EMPTY,
            Piece.EMPTY,                                    Piece.EMPTY,                                    Piece.EMPTY
        ),
        pieceToMove = Piece.BLUE_,
        removalCount = 1u
    )

    @Test
    fun `evaluation test4`() {
        assertEquals(evaluationPosition4.evaluate(),  Pair((-128).toByte(), 127.toByte()))
    }

    private val evaluationPosition5 = Position(
        mutableListOf(
            Piece.BLUE_,                                    Piece.GREEN,                                    Piece.GREEN,
                            Piece.BLUE_,                    Piece.EMPTY,                    Piece.EMPTY,
                                            Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,
            Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,                    Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,
                                            Piece.BLUE_,    Piece.EMPTY,    Piece.EMPTY,
                            Piece.EMPTY,                    Piece.EMPTY,                    Piece.EMPTY,
            Piece.EMPTY,                                    Piece.EMPTY,                                    Piece.EMPTY
        ),
        freePieces = Pair(1u, 0u),
        pieceToMove = Piece.BLUE_,
        removalCount = 1u
    )

    @Test
    fun `evaluation test5`() {
        assertEquals(evaluationPosition5.evaluate(),  Pair((54).toByte(), (-24).toByte()))
    }

    private val evaluationPosition6 = Position(
        mutableListOf(
            Piece.BLUE_,                                    Piece.GREEN,                                    Piece.GREEN,
                            Piece.EMPTY,                    Piece.EMPTY,                    Piece.EMPTY,
                                            Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,
            Piece.EMPTY,    Piece.EMPTY,    Piece.BLUE_,                    Piece.EMPTY,    Piece.EMPTY,    Piece.EMPTY,
                                            Piece.BLUE_,    Piece.EMPTY,    Piece.EMPTY,
                            Piece.EMPTY,                    Piece.EMPTY,                    Piece.EMPTY,
            Piece.EMPTY,                                    Piece.EMPTY,                                    Piece.EMPTY
        ),
        freePieces = Pair(1u, 0u),
        pieceToMove = Piece.BLUE_,
        removalCount = 1u
    )

    @Test
    fun `evaluation test6`() {
        assertEquals(evaluationPosition5.evaluate(),  Pair((54).toByte(), -24.toByte()))
    }
}
