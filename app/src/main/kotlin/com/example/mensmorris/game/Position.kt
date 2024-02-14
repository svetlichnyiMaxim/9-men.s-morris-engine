package com.example.mensmorris.game

/**
 * used for storing position data
 * @param positions all pieces
 * @param freePieces pieces we can still place: first - green, second - blue
 * @param greenPiecesAmount used for fast evaluation & game state checker (stores green pieces)
 * @param bluePiecesAmount used for fast evaluation & game state checker (stores blue pieces)
 * @param pieceToMove piece going to move next
 * @param removalCount amount of pieces to remove
 */
class Position(
    var positions: MutableList<Piece>,
    var freePieces: Pair<UByte, UByte> = Pair(0U, 0U),
    var greenPiecesAmount: UByte,
    var bluePiecesAmount: UByte,
    var pieceToMove: Boolean,
    var removalCount: UByte = 0U
) {
    constructor(
        positions: MutableList<Piece>,
        freePieces: Pair<UByte, UByte> = Pair(0U, 0U),
        pieceToMove: Boolean,
        removalCount: UByte = 0U
    ) : this(positions,
        freePieces,
        (positions.count { it.isGreen == true } + freePieces.first),
        (positions.count { it.isGreen == false } + freePieces.second),
        pieceToMove,
        removalCount)

    /**
     * evaluates position
     * @return pair, where first is eval. for green and the second one for blue
     */
    fun evaluate(depth: UByte = 0u): Pair<Int, Int> {
        if (greenPiecesAmount < PIECES_TO_FLY) {
            return Pair(Int.MIN_VALUE, Int.MAX_VALUE)
        }
        if (bluePiecesAmount < PIECES_TO_FLY) {
            return Pair(Int.MAX_VALUE, Int.MIN_VALUE)
        }
        val greenPieces = (greenPiecesAmount.toInt() + if (pieceToMove) removalCount.toInt() else 0)
        val bluePieces = (bluePiecesAmount.toInt() + if (!pieceToMove) removalCount.toInt() else 0)

        val basicGreenEval = (greenPieces - bluePieces) * PIECE_COST
        val basicBlueEval = (bluePieces - greenPieces) * PIECE_COST

        val unfinishedTriples = unfinishedTriples()
        val greenUnfinishedTriplesEval =
            (unfinishedTriples.first - unfinishedTriples.second * ENEMY_UNFINISHED_TRIPLES_COST) *
                    UNFINISHED_TRIPLES_COST
        val blueUnfinishedTriplesEval =
            (unfinishedTriples.second - unfinishedTriples.first * ENEMY_UNFINISHED_TRIPLES_COST) *
                    UNFINISHED_TRIPLES_COST

        val findBlockedTriples = findBlockedTriples()
        val greenPossibleTriplesEval =
            (findBlockedTriples.first - findBlockedTriples.second) * POSSIBLE_TRIPLE_COST
        val bluePossibleTriplesEval =
            (findBlockedTriples.second - findBlockedTriples.first) * POSSIBLE_TRIPLE_COST

        val greenEvaluation =
            (basicGreenEval + greenUnfinishedTriplesEval + greenPossibleTriplesEval + depth.toInt())
        val blueEvaluation =
            (basicBlueEval + blueUnfinishedTriplesEval + bluePossibleTriplesEval + depth.toInt())

        return Pair(greenEvaluation, blueEvaluation)
    }

    /**
     * @return amount of lines with only 1 missing piece for new removal (triple)
     */
    fun unfinishedTriples(): Pair<Int, Int> {
        var greenUnfinishedTriples = 0
        var blueUnfinishedTriples = 0
        for (ints in triplesMap) {
            // green
            if (ints.none { positions[it].isGreen == false } && ints.count { positions[it].isGreen == true } == 2) {
                greenUnfinishedTriples++
            }
            // blue
            if (ints.none { positions[it].isGreen == true } && ints.count { positions[it].isGreen == false } == 2) {
                blueUnfinishedTriples++
            }
        }
        return Pair(greenUnfinishedTriples, blueUnfinishedTriples)
    }

    private fun findBlockedTriples(): Pair<Int, Int> {
        var greenBlockedTriples = 0
        var blueBlockedTriples = 0
        for (ints in triplesMap) {
            // green
            if (ints.count { positions[it].isGreen == false } == 1 &&
                ints.count { positions[it].isGreen == true } == 2) {
                greenBlockedTriples++
            }
            // blue
            if (ints.count { positions[it].isGreen == true } == 1 &&
                ints.count { positions[it].isGreen == false } == 2) {
                blueBlockedTriples++
            }
        }
        return Pair(greenBlockedTriples, blueBlockedTriples)
    }

    /**
     * @return true if game has ended
     */
    private fun gameEnded(): Boolean {
        return greenPiecesAmount < PIECES_TO_FLY || bluePiecesAmount < PIECES_TO_FLY
    }

    /**
     * @param depth current depth
     * @color color of the piece we are finding a move for
     * @return possible positions and there evaluation
     */
    fun solve(
        depth: UByte
    ): Pair<Pair<Int, Int>, MutableList<Position>> {
        if (depth == 0.toUByte() || gameEnded()) {
            return Pair(evaluate(depth), mutableListOf(this))
        }
        // for all possible positions, we try to solve them
        val positions = (generatePositions(depth).map {
            it.solve((depth - 1u).toUByte())
        }.filter { it.second.isNotEmpty() })
        if (positions.isEmpty()) {
            // if we can't make a move, we lose
            return Pair(
                if (!pieceToMove) {
                    Pair(Int.MIN_VALUE, Int.MAX_VALUE)
                } else {
                    Pair(Int.MAX_VALUE, Int.MIN_VALUE)
                }, mutableListOf(this)
            )
        }
        return positions.maxBy {
            it.first[pieceToMove]
        }.apply { this.second.add(this@Position) }
    }

    /**
     * @return a copy of the current position
     */
    fun copy(): Position {
        return Position(
            positions.map { it.copy() }.toMutableList(),
            freePieces,
            greenPiecesAmount,
            bluePiecesAmount,
            pieceToMove,
            removalCount
        )
    }

    /**
     * @param currentDepth the current depth we are at
     * @return possible positions we can achieve in 1 move
     */
    fun generatePositions(currentDepth: UByte): List<Position> {
        val str = toString()
        // check if we can abort calculation / use our previous result
        occurredPositions[str]?.let {
            if (it.second >= currentDepth) {
                return listOf()
            } else {
                occurredPositions[str] = Pair(it.first, currentDepth)
                return it.first
            }
        }
        val generatedList = generateMoves().map {
            it.producePosition(this)
        }
        // store our work into our hashMap
        occurredPositions[str] = Pair(generatedList, currentDepth)
        return generatedList
    }

    /**
     * @param move the last move we have performed
     * @return the amount of removes we need to perform
     */
    fun removalAmount(move: Movement): UByte {
        if (move.endIndex == null) return 0U

        return removeChecker[move.endIndex]!!.count { list ->
            list.all { positions[it].isGreen == pieceToMove }
        }.toUByte()
    }

    /**
     * @return possible movements
     */
    fun generateMoves(): List<Movement> {
        return when (gameState()) {
            GameState.Placement -> {
                generatePlacementMovements()
            }

            GameState.End -> {
                listOf()
            }

            GameState.Flying -> {
                generateFlyingMovements()
            }

            GameState.Normal -> {
                generateNormalMovements()
            }

            GameState.Removing -> {
                generateRemovalMoves()
            }
        }
    }

    private fun generateRemovalMoves(): List<Movement> {
        val possibleMove: MutableList<Movement> = mutableListOf()
        positions.forEachIndexed { index, piece ->
            if (piece.isGreen != null && piece.isGreen != pieceToMove) {
                possibleMove.add(Movement(index, null))
            }
        }
        return possibleMove
    }

    /**
     * @return all possible normal movements
     */
    private fun generateNormalMovements(): List<Movement> {
        val possibleMove: MutableList<Movement> = mutableListOf()
        positions.forEachIndexed { startIndex, piece ->
            if (piece.isGreen == pieceToMove) {
                moveProvider[startIndex]!!.forEach { endIndex ->
                    if (positions[endIndex].isGreen == null) {
                        possibleMove.add(Movement(startIndex, endIndex))
                    }
                }
            }
        }
        return possibleMove
    }

    /**
     * @return all possible flying movements
     */
    private fun generateFlyingMovements(): List<Movement> {
        val possibleMove: MutableList<Movement> = mutableListOf()
        positions.forEachIndexed { startIndex, piece ->
            if (piece.isGreen == pieceToMove) {
                positions.forEachIndexed { endIndex, endPiece ->
                    if (endPiece.isGreen == null) {
                        possibleMove.add(Movement(startIndex, endIndex))
                    }
                }
            }
        }
        return possibleMove
    }

    /**
     * @return possible piece placements
     */
    private fun generatePlacementMovements(): List<Movement> {
        val possibleMove: MutableList<Movement> = mutableListOf()
        positions.forEachIndexed { endIndex, piece ->
            if (piece.isGreen == null) {
                possibleMove.add(Movement(null, endIndex))
            }
        }
        return possibleMove
    }

    /**
     * @return state of the game
     */
    fun gameState(): GameState {
        return when {
            (gameEnded()) -> {
                GameState.End
            }

            (removalCount > 0U) -> {
                GameState.Removing
            }

            (freePieces[pieceToMove] > 0U) -> {
                GameState.Placement
            }

            ((pieceToMove && greenPiecesAmount == PIECES_TO_FLY) ||
                    (!pieceToMove && bluePiecesAmount == PIECES_TO_FLY)) -> {
                GameState.Flying
            }

            else -> GameState.Normal
        }
    }


    /**
     * displays position
     * used for testing purpose
     */
    fun display() {
        val c = positions.map {
            if (it.isGreen == null) {
                //GRAY_CIRCLE
                CIRCLE
            } else {
                if (!it.isGreen!!) {
                    //BLUE_CIRCLE
                    BLUE + CIRCLE + NONE
                } else {
                    //GREEN_CIRCLE
                    GREEN + CIRCLE + NONE
                }
            }
        }
        println(
            """$NONE
        ${c[0]}-----------------${c[1]}-----------------${c[2]}
        |                  |                  |
        |     ${c[3]}-----------${c[4]}-----------${c[5]}     |
        |     |            |            |     |
        |     |     ${c[6]}-----${c[7]}-----${c[8]}     |     |
        |     |     |             |     |     |
        ${c[9]}----${c[10]}----${c[11]}             ${c[12]}----${c[13]}----${c[14]}
        |     |     |             |     |     |
        |     |     ${c[15]}-----${c[16]}-----${c[17]}     |     |
        |     |            |            |     |
        |     ${c[18]}-----------${c[19]}-----------${c[20]}     |
        |                  |                  |
        ${c[21]}-----------------${c[22]}-----------------${c[23]}
        """.trimIndent()
        )
        println()
    }

    fun display2() {
        val c = positions.map {
            if (it.isGreen == null) {
                "empty()"
            } else {
                if (it.isGreen == false) {
                    "blue()"
                } else {
                    "green()"
                }
            }
        }
        println(
            """
        Position(
            mutableListOf(
                ${c[0]},                                    ${c[1]},                                     ${c[2]},
                                ${c[3]},                    ${c[4]},                    ${c[5]},
                                            ${c[6]},        ${c[7]},        ${c[8]},
                ${c[9]},        ${c[10]},   ${c[11]},                       ${c[12]},   ${c[13]},        ${c[14]},
                                            ${c[15]},       ${c[16]},       ${c[17]},
                                ${c[18]},                   ${c[19]},                   ${c[20]},
                ${c[21]},                                   ${c[22]},                                    ${c[23]}
            ),
            freePieces = Pair(${freePieces.first}u, ${freePieces.second}u),
            pieceToMove = ${pieceToMove},
            removalCount = ${removalCount}u
        )
        """.trimIndent()
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Position) {
            return super.equals(other)
        }
        for (i in 0..<positions.size) {
            if (positions[i] != other.positions[i]) {
                return false
            }
        }
        return freePieces == other.freePieces && pieceToMove == other.pieceToMove
    }

    override fun toString(): String {
        return (if (pieceToMove) "0" else "1") + removalCount.toString() + positions.joinToString(
            separator = ""
        ) {
            it.toString()
        } + " " + freePieces.first + " " + freePieces.second
    }
}

private operator fun <A> Pair<A, A>.get(first: Boolean): A {
    return if (first) this.first
    else this.second
}

private operator fun Int.plus(uInt: UInt): UInt {
    return (this + uInt.toInt()).toUInt()
}

private operator fun Int.plus(uByte: UByte): UByte {
    return (this + uByte.toInt()).toUByte()
}

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
const val BLUE_CIRCLE: String = "\uD83D\uDD35"

/**
 * green circle unicode symbol
 * windows only
 */
const val GREEN_CIRCLE: String = "\uD83D\uDFE2"

/**
 * gray circle unicode symbol
 * windows only
 */
const val GRAY_CIRCLE: String = "⚪"
