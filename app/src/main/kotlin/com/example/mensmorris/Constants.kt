package com.example.mensmorris

/**
 * pieces cost
 */
const val PIECE_COST = 1_000

/**
 * how much danger enemy unfinished triples are
 */
const val ENEMY_UNFINISHED_TRIPLES_COST = 3

/**
 * how much unfinished triple costs
 */
const val UNFINISHED_TRIPLES_COST = 200

/**
 * how much possible triple costs
 */
const val POSSIBLE_TRIPLE_COST = 5

/**
 * needed pieces to be able to fly over the board
 */
const val PIECES_TO_FLY: UByte = 3U

/**
 * a constant for lost games
 * we need it to be not INT.MIN_VALUE cause we still want the least worst lost positions
 */
const val LOST_GAME_COST = -2_147_483_648 + 1_000_000

/**
 * fast way for creating green piece
 */
const val GREEN = true

/**
 * fast way for creating blue piece
 */
const val BLUE_ = false

/**
 * fast way for creating empty piece
 */
inline val EMPTY: Boolean? get() = null

/**
 * circle unicode symbol
 * linux only
 */
const val CIRCLE: String = "\uD83D\uDD35"

/**
 * blue color
 * linux only
 */
const val BLUE_COLOR: String = "\u001B[34m"

/**
 * green color
 * linux only
 */
const val GREEN_COLOR: String = "\u001B[32m"

/**
 * resets to default color
 * linux only
 */
const val NONE_COLOR: String = "\u001B[90m"
