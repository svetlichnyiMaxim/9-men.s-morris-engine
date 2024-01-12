private val <E> List<E>.toTriple: Triple<E, E, E>
    get() {
        require(size == 3)
        return Triple(this[0], this[1], this[2])
    }

/**
 * used for storing position data
 * @param positions all pieces
 * @param freePieces pieces we can still place: first - green, second - blue
 * @param pieceToMove piece going to move next
 */
class Position(
    var positions: Triple<MutableCollection<UByte>, MutableCollection<UByte>, MutableCollection<UByte>>,
    var freePieces: Pair<Int, Int> = Pair(0, 0),
    var pieceToMove: Piece
) {
    constructor(
        positions: MutableList<Piece>, freePieces: Pair<Int, Int> = Pair(0, 0), pieceToMove: Piece
    ) : this(positions.mapIndexed { index, piece -> Pair(index.toUByte(), piece) }.groupBy { it.second }.toList()
        .sortedBy { it.first.index }.map { it1 -> it1.second.map { it.first }.toMutableList() }.toTriple,
        freePieces,
        pieceToMove
    )

    override fun equals(other: Any?): Boolean {
        if (other !is Position) {
            return super.equals(other)
        }
        other.positions.let {
            return positions == it && freePieces == other.freePieces && pieceToMove == other.pieceToMove
        }
    }

    override fun toString(): String {
        return pieceToMove.index.toString() +
                positions.first.joinToString { it.toString() } + "/" + positions.second.joinToString { it.toString() } +
                "|" + freePieces.first + "|" + freePieces.second
    }

    /**
     * @param depth current depth
     * @color color of the piece we are finding a move for
     * @return possible positions and there evaluation
     */
    fun solve(
        depth: Int
    ): Pair<Pair<Int, Int>, MutableList<Position>> {
        if (depth == 0 || gameEnded() != null) {
            return Pair(advantage(), mutableListOf(this))
        }
        // for all possible positions, we try to solve them
        return (generatePositions(pieceToMove, depth).map {
            it.apply { it.pieceToMove = it.pieceToMove.opposite() }.solve(depth - 1)
        }.filter { it.second.isNotEmpty() }.maxByOrNull { it.first[pieceToMove.index] }
        // if we can't make a move, we lose
            ?: return Pair(
                if (pieceToMove == Piece.GREEN) {
                    Pair(Int.MIN_VALUE, Int.MAX_VALUE)
                } else {
                    Pair(Int.MAX_VALUE, Int.MIN_VALUE)
                }, mutableListOf()
            )).apply { second.add(this@Position) }
    }

    /**
     * @return a copy of the current position
     */
    fun copy(): Position {
        return Position(
            Triple(positions.first.toMutableList(), positions.second.toMutableList(), positions.third.toMutableList()),
            freePieces,
            pieceToMove
        )
    }

    /**
     * @param color color of the piece
     * @return number of pieces with specified color
     */
    private fun countPieces(color: Piece): Int {
        return positions[color.index].size
    }

    /**
     * @return if the game has ended
     */
    private fun gameEnded(): Piece? {
        for (i in 0..1) {
            if (countPieces(colorMap[i]!!) + freePieces[i.toUByte()] < 3) return colorMap[i]!!
        }
        return null
    }

    /**
     * @return advantage of pieces with current color
     */
    fun advantage(): Pair<Int, Int> {
        return when (gameEnded()) {
            Piece.GREEN -> Pair(Int.MIN_VALUE, Int.MAX_VALUE)
            Piece.BLUE_ -> Pair(Int.MAX_VALUE, Int.MIN_VALUE)
            else -> {
                ((countPieces(Piece.GREEN) - countPieces(Piece.BLUE_)) + (freePieces[0U] - freePieces[1U])).let {
                    Pair(it, -it)
                }
            }
        }
    }

    /**
     * @param color color of the piece
     * @return indexes with pieces of the needed color
     */
    private fun indexes(color: Piece): MutableCollection<UByte> {
        return positions[color.index]
    }

    /**
     * @param color color of the piece
     * @param currentDepth the current depth we are at
     * @return possible positions we can achieve in 1 move
     */
    private fun generatePositions(color: Piece, currentDepth: Int): List<Position> {
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
        val generatedList = generateMoves(color).flatMap {
            val position = it.producePosition(this)
            val removalAmount = position.removalAmount(it)
            // if we doesn't remove anything - skip
            if (removalAmount == 0) listOf(position) else position.generatePositionsAfterRemoval(
                removalAmount, position.pieceToMove
            )
        }
        // store our work into our hashMap
        occurredPositions[str] = Pair(generatedList, currentDepth)
        return generatedList
    }

    /**
     * @param amount number of removals
     * @param color color of the piece
     * @return generated positions after removal
     */
    private fun generatePositionsAfterRemoval(amount: Int, color: Piece): List<Position> {
        var positions = listOf(this)
        repeat(amount) {
            positions = positions.flatMap { position ->
                position.indexes(color.opposite()).map { Movement(it, null).producePosition(position) }
            }
        }
        return positions
    }

    private fun checkLine(list: List<UByte>): Boolean {
        return positions[pieceToMove.index].containsAll(list)
    }

    /**
     * @param move the last move we have performed
     * @return the amount of removes we need to perform
     */
    private fun removalAmount(move: Movement): Int {
        return removeChecker[move.endIndex]!!.count { checkLine(it) }
    }

    /**
     * @param color color of the piece
     * @return possible movements
     */
    private fun generateMoves(color: Piece): List<Movement> {
        when (gameState(color)) {
            GameState.Placement -> {
                return generatePlacementMovements()
            }

            GameState.End -> {
                return listOf()
            }

            GameState.Flying -> {
                return generateFlyingMovements(color)
            }

            GameState.Normal -> {
                return generateNormalMovements(color)
            }
        }
    }


    /**
     * @param color color of the piece
     * @return all possible normal movements
     */
    private fun generateNormalMovements(color: Piece): List<Movement> {
        return indexes(color).flatMap { startIndex ->
            moveProvider[startIndex]!!.filter { endIndex ->
                positions[2U].contains(endIndex)
            }.map { endIndex -> Movement(startIndex, endIndex) }
        }
    }

    /**
     * @param color color of the piece
     * @return all possible flying movements
     */
    private fun generateFlyingMovements(color: Piece): List<Movement> {
        return indexes(color).flatMap { start -> indexes(Piece.EMPTY).map { end -> Movement(start, end) } }
    }

    /**
     * @return possible piece placements
     */
    private fun generatePlacementMovements(): List<Movement> {
        return indexes(Piece.EMPTY).map { Movement(null, it) }
    }

    /**
     * @param color color of the piece
     * @return state of the game
     */
    private fun gameState(color: Piece): GameState {
        return when {
            (gameEnded() != null) -> {
                GameState.End
            }

            (freePieces[color.index] > 0) -> {
                GameState.Placement
            }

            (countPieces(color) == 3) -> {
                GameState.Flying
            }

            else -> GameState.Normal
        }
    }

    /**
     * displays position in a human-readable form
     */
    fun display() {
        val c = positions.toList().flatMapIndexed { index: Int, uBytes: MutableCollection<UByte> ->
            uBytes.map {
                Pair(
                    it, colorMap[index]!!
                )
            }
        }.sortedBy { it.first }.map {
            when (it.second) {
                Piece.BLUE_ -> {
                    //BLUE_CIRCLE
                    BLUE + CIRCLE + NONE
                }

                Piece.GREEN -> {
                    //GREEN_CIRCLE
                    GREEN + CIRCLE + NONE
                }

                Piece.EMPTY -> {
                    //GRAY_CIRCLE
                    CIRCLE
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
}
