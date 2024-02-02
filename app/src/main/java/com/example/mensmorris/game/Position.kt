package com.example.mensmorris.game

import com.example.mensmorris.toTriple

/**
 * used for storing position data
 * @param positions all pieces
 * @param freePieces pieces we can still place: first - green, second - blue
 * @param pieceToMove piece going to move next
 * @param removalCount amount of pieces to remove
 */
class Position(
    var positions: Triple<MutableCollection<UByte>, MutableCollection<UByte>, MutableCollection<UByte>>,
    var freePieces: Pair<UByte, UByte> = Pair(0U, 0U),
    var pieceToMove: Piece,
    var removalCount: UByte = 0U
) {
    constructor(
        positions: MutableList<Piece>,
        freePieces: Pair<UByte, UByte> = Pair(0U, 0U),
        pieceToMove: Piece,
        removalCount: UByte = 0U
    ) : this(positions.mapIndexed { index, piece -> Pair(index.toUByte(), piece) }
        .groupBy { it.second }.toList().sortedBy { it.first.index }
        .map { it1 -> it1.second.map { it.first }.toMutableList() }.toTriple,
        freePieces,
        pieceToMove,
        removalCount
    )

    /**
     * @param index index we are trying to get color for
     * @return piece with provided index
     * @throws IllegalStateException if we weren't able to find needed index
     */
    fun getIndexColor(index: UByte): Piece {
        positions.toList().forEachIndexed { columnIndex, list ->
            list.forEach {
                if (it == index) {
                    return colorMap[columnIndex]!!
                }
            }
        }
        error("Some piece index is missing")
    }

    /**
     * evaluates position
     * @return pair, where first is eval. for green and the second one for blue
     */
    fun evaluate(depth: UByte = 0u): Pair<Byte, Byte> {
        if (countPieces(Piece.GREEN) + freePieces[Piece.GREEN.index] < 3U) {
            return Pair(Byte.MIN_VALUE, Byte.MAX_VALUE)
        }
        if (countPieces(Piece.BLUE_) + freePieces[Piece.BLUE_.index] < 3U) {
            return Pair(Byte.MAX_VALUE, Byte.MIN_VALUE)
        }
        val bluePieces =
            (countPieces(Piece.BLUE_) + freePieces[1U]).toByte() + if (pieceToMove == Piece.BLUE_) removalCount.toByte() else 0
        val greenPieces =
            (countPieces(Piece.GREEN) + freePieces[0U]).toByte() + if (pieceToMove == Piece.GREEN) removalCount.toByte() else 0
        val greenEvaluation = ((greenPieces - bluePieces) * 1000 + findPossibleTriple().first * 15u + depth).toByte()
        val blueEvaluation = ((bluePieces - greenPieces) * 1000 + findPossibleTriple().second * 15u + depth).toByte()
        return Pair(greenEvaluation, blueEvaluation)
    }

    /**
     * @return amount of 2-s close to each other
     */
    fun findPossibleTriple(): Pair<UByte, UByte> {
        var resultGreen = 0U
        var resultBlue = 0u
        positions[1u].forEach { element ->
            resultGreen += removeChecker[element]!!.sumOf { possiblePositions ->
                positions[1u].count { possiblePositions.contains(it) }
            }.toUByte()
        }
        positions[2u].forEach { position ->
            resultBlue += removeChecker[position]!!.sumOf { possiblePositions ->
                positions[2u].count { possiblePositions.contains(position) }
            }.toUByte()
        }
        return Pair(resultGreen.toUByte(), resultBlue.toUByte())
    }
    /**
     * @param depth current depth
     * @color color of the piece we are finding a move for
     * @return possible positions and there evaluation
     */
    fun solve(
        depth: UByte
    ): Pair<Pair<Byte, Byte>, MutableList<Position>> {
        if (depth == 0.toUByte()) {
            return Pair(evaluate(), mutableListOf(this))
        }
        // for all possible positions, we try to solve them
        val positions = (generatePositions(depth).map {
            it.solve((depth - 1u).toUByte())
        }.filter { it.second.isNotEmpty() })
        if (positions.isEmpty()) {
            // if we can't make a move, we lose
            return Pair(
                if (pieceToMove == Piece.GREEN) {
                    Pair(Byte.MIN_VALUE, Byte.MAX_VALUE)
                } else {
                    Pair(Byte.MAX_VALUE, Byte.MIN_VALUE)
                }, mutableListOf(this)
            )
        }
        val bestPosition = positions.maxBy {
            it.first[pieceToMove.index]
        }
        bestPosition.second.add(this)
        return bestPosition
    }

    /**
     * @return a copy of the current position
     */
    fun copy(): Position {
        return Position(
            Triple(
                positions.first.toMutableList(),
                positions.second.toMutableList(),
                positions.third.toMutableList()
            ), freePieces, pieceToMove, removalCount
        )
    }

    /**
     * @param color color of the piece
     * @return number of pieces with specified color
     */
    private fun countPieces(color: Piece): UByte {
        return positions[color.index].size.toUByte()
    }

    /**
     * @param color color of the piece
     * @return indexes with pieces of the needed color
     */
    private fun indexes(color: Piece): MutableCollection<UByte> {
        return positions[color.index]
    }

    /**
     * @param currentDepth the current depth we are at
     * @return possible positions we can achieve in 1 move
     */
    private fun generatePositions(currentDepth: UByte): List<Position> {
        val str = toString()
        // check if we can abort calculation / use our previous result
        occurredPositions[str]?.let {
            if (it.second >= currentDepth + 1u) {
                return listOf()
            } else {
                occurredPositions[str] = Pair(it.first, currentDepth)
                return it.first
            }
        }
        val generatedList = generateMoves().map {
            val position = it.producePosition(this)
            position
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

        return removeChecker[move.endIndex]!!.count {
            positions[pieceToMove.index].containsAll(it)
        }.toUByte()
    }

    /**
     * @return possible movements
     */
    fun generateMoves(): List<Movement> {
        when (gameState()) {
            GameState.Placement -> {
                return generatePlacementMovements()
            }

            GameState.End -> {
                return listOf()
            }

            GameState.Flying -> {
                return generateFlyingMovements()
            }

            GameState.Normal -> {
                return generateNormalMovements()
            }

            GameState.Removing -> {
                return generateRemovalMoves()
            }
        }
    }

    private fun generateRemovalMoves(): List<Movement> {
        return this.indexes(this.pieceToMove.opposite()).map { Movement(it, null) }
    }

    /**
     * @return all possible normal movements
     */
    private fun generateNormalMovements(): List<Movement> {
        return indexes(pieceToMove).flatMap { startIndex ->
            moveProvider[startIndex]!!.filter { endIndex ->
                positions[2U].contains(endIndex)
            }.map { endIndex -> Movement(startIndex, endIndex) }
        }
    }

    /**
     * @return all possible flying movements
     */
    private fun generateFlyingMovements(): List<Movement> {
        return indexes(pieceToMove).flatMap { start ->
            indexes(Piece.EMPTY).map { end ->
                Movement(
                    start, end
                )
            }
        }
    }

    /**
     * @return possible piece placements
     */
    private fun generatePlacementMovements(): List<Movement> {
        return indexes(Piece.EMPTY).map { Movement(null, it) }
    }

    /**
     * @return state of the game
     */
    fun gameState(): GameState {
        return when {
            ((countPieces(Piece.GREEN) + freePieces[Piece.GREEN.index] < 3U) || (countPieces(Piece.BLUE_) + freePieces[Piece.BLUE_.index] < 3U)) -> {
                GameState.End
            }

            (removalCount > 0U) -> {
                GameState.Removing
            }

            (freePieces[pieceToMove.index] > 0U) -> {
                GameState.Placement
            }

            (countPieces(pieceToMove) == 3.toUByte()) -> {
                GameState.Flying
            }

            else -> GameState.Normal
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Position) {
            return super.equals(other)
        }
        other.positions.let {
            return positions == it && freePieces == other.freePieces && pieceToMove == other.pieceToMove
        }
    }

    override fun toString(): String {
        return removalCount.toString() + "!" + pieceToMove.index.toString() + "!/" + positions.first.joinToString { it.toString() } + "/" + positions.second.joinToString { it.toString() } + "/" + freePieces.first + "|" + freePieces.second
    }
}

private operator fun Int.plus(uInt: UInt): UInt {
    return (this + uInt.toInt()).toUInt()
}

private operator fun Int.plus(uByte: UByte): UByte {
    return (this + uByte.toInt()).toUByte()
}
