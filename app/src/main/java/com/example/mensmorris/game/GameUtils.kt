package com.example.mensmorris.game

import androidx.compose.ui.graphics.Color
import kotlin.math.abs

val gameStartPosition = Position(
    mutableListOf(
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.BLUE_,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.GREEN,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY
    ), Pair(6u, 6u), pieceToMove = Piece.BLUE_
)

/**
 * we store occurred positions here which massively increases speed
 */
val occurredPositions: HashMap<String, Pair<List<Position>, UByte>> = hashMapOf()

/**
 * used for storing piece color
 * @param index index of free pieces in Position class
 */
enum class Piece(val index: UByte, val color: Color) {
    /**
     * green color
     */
    GREEN(0U, Color.Green),

    /**
     * blue color
     */
    BLUE_(1U, Color.Blue),

    /**
     * no piece is placed
     */
    EMPTY(2U, Color.Black)
}

/**
 * @return opposite color
 */
fun Piece.opposite(): Piece {
    return colorMap[abs(1 - this.index.toInt())]!!
}

/**
 * used for storing game state
 */
enum class GameState {
    /**
     * game starting part, we simply place pieces
     */
    Placement,

    /**
     * normal part of the game
     */
    Normal,

    /**
     * part of the game where pieces can fly
     */
    Flying,

    /**
     * if game has ended xd
     */
    End,

    /**
     * if you are removing a piece
     */
    Removing
}
