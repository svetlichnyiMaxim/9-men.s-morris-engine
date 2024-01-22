package com.example.mensmorris

import androidx.compose.ui.graphics.Color
import kotlin.math.abs

/**
 * circle unicode symbol
 * linux only
 */
const val CIRCLE: String = "\uD83D\uDD35"

/**
 * blue color
 * linux only
 */
const val BLUE: String = "\u001B[34m"

/**
 * green color
 * linux only
 */
const val GREEN: String = "\u001B[32m"

/**
 * resets to default color
 * linux only
 */
const val NONE: String = "\u001B[90m"

/**
 * blue circle unicode symbol
 * windows only
 */
@Suppress("unused")
const val BLUE_CIRCLE: String = "\uD83D\uDD35"

/**
 * green circle unicode symbol
 * windows only
 */
@Suppress("unused")
const val GREEN_CIRCLE: String = "\uD83D\uDFE2"

/**
 * gray circle unicode symbol
 * windows only
 */
@Suppress("unused")
const val GRAY_CIRCLE: String = "⚪"

/**
 * we store occurred positions here which massively increases speed
 */
val occurredPositions: HashMap<String, Pair<List<Position>, UByte>> = hashMapOf()

/**
 * an example position for test purposes
 */
@Suppress("IncorrectFormatting")
val examplePosition = Position(
    mutableListOf(
        Piece.EMPTY, Piece.EMPTY, Piece.EMPTY,
        Piece.EMPTY, Piece.EMPTY, Piece.EMPTY,
        Piece.EMPTY, Piece.BLUE_, Piece.BLUE_,
        Piece.GREEN, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY,
        Piece.EMPTY, Piece.EMPTY, Piece.EMPTY,
        Piece.EMPTY, Piece.EMPTY, Piece.EMPTY,
        Piece.GREEN, Piece.EMPTY, Piece.EMPTY
    ),
    freePieces = Pair(3u, 3u),
    pieceToMove = Piece.BLUE_,
    removalCount = 0u
)

/*
0-----------------1-----------------2
|                 |                 |
|     3-----------4-----------5     |
|     |           |           |     |
|     |     6-----7-----8     |     |
|     |     |           |     |     |
9-----10----11          12----13----14
|     |     |           |     |     |
|     |     15----16----17    |     |
|     |           |           |     |
|     18----------19----------20    |
|                 |                 |
21----------------22----------------23
 */

/**
 * part of the program it starts from
 */
fun main() {
    val b = examplePosition.solve(3u).second
    //examplePosition.generatePositions(Piece.GREEN).forEach { it.display() }
    b.forEach {
        it.display()
        println("${it.evaluate()} and move - ${it.pieceToMove} removals - ${it.removalCount}")
    }
}

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
