private val <E> List<E>.toTriple: Triple<E, E, E>
    get() {
        require(this.size == 3)
        return Triple(this[0], this[1], this[2])
    }

/**
 * used for storing position data
 * @param positions all pieces
 * @param freePieces pieces we can still place: first - green, second - blue
 */
data class Position(
    var positions: Triple<MutableCollection<UByte>, MutableCollection<UByte>, MutableCollection<UByte>>,
    var freePieces: Pair<Int, Int> = Pair(0, 0),
    var pieceToMove: Piece
) {
    constructor(
        positions: MutableList<Piece>, freePieces: Pair<Int, Int> = Pair(0, 0), pieceToMove: Piece
    ) : this(positions.mapIndexed { index, piece -> Pair(index.toUByte(), piece) }.groupBy { it.second }.toList()
        .sortedBy { it.first.index }.map { it.second }.map { it1 -> it1.map { it.first }.toMutableList() }.toTriple,
        freePieces,
        pieceToMove
    )

    override fun equals(other: Any?): Boolean {
        if (other is Position) {
            return positions.first == other.positions.first && positions.second == other.positions.second && positions.third == other.positions.third && freePieces == other.freePieces && pieceToMove == other.pieceToMove
        }
        return super.equals(other)
    }

    override fun toString(): String {
        return pieceToMove.index.toString() + positions.first.joinToString { it.toString() } + "/" + positions.second.joinToString { it.toString() } + "|" + freePieces.first + "|" + freePieces.second
    }

    /**
     * @param depth current depth
     * @color color of the piece we are finding a move for
     * @return possible positions and there evaluation
     */
    fun solve(
        depth: Int
    ): Pair<Int, MutableList<Position>> {
        if (depth == 0 || this.gameEnded() != null) {
            return Pair(this.advantage(this.pieceToMove), mutableListOf(this))
        }
        // for all possible positions, we try to solve them
        return (this.generatePositions(this.pieceToMove, depth)
            .map { it.apply { it.pieceToMove = it.pieceToMove.opposite() }.solve(depth - 1) }
            .filter { it.second.isNotEmpty() }.maxByOrNull { it.second.first().advantage(this.pieceToMove) }
            ?: return Pair(
                Int.MIN_VALUE, mutableListOf()
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
        if (countPieces(Piece.GREEN) + freePieces[Piece.GREEN.index] < 3) return Piece.GREEN
        if (countPieces(Piece.BLUE_) + freePieces[Piece.BLUE_.index] < 3) return Piece.BLUE_
        return null
    }

    /**
     * @param color color of the piece
     * @return advantage of pieces with current color
     */
    fun advantage(color: Piece): Int {
        return when (gameEnded()) {
            color.opposite() -> Int.MAX_VALUE
            color -> Int.MIN_VALUE
            else -> (countPieces(color) + freePieces[color.index]) - (countPieces(color.opposite()) + freePieces[color.opposite().index])
        }
    }

    /**
     * @param color color of the piece
     * @return indexes with pieces of the needed color
     */
    private fun indexes(color: Piece): MutableCollection<UByte> {
        return positions.get(color.index)
    }

    /**
     * @param color color of the piece
     * @param currentDepth the current depth we are at
     * @return possible positions we can achieve in 1 move
     */
    private fun generatePositions(color: Piece, currentDepth: Int): List<Position> {
        val str = this.toString()
        occurredPositions[str]?.let {
            if (it.second >= currentDepth) {
                return listOf()
            } else {
                occurredPositions[str] = Pair(it.first, currentDepth)
                return it.first
            }
        }
        val generatedList = generateMoves(color).map { Pair(it, it.producePosition(this)) }.map {
            val removalAmount = it.second.removalAmount(it.first)
            if (removalAmount == 0) listOf(it.second) else it.second.generatePositionsAfterRemoval(
                removalAmount, it.second.pieceToMove
            )
        }.flatten()
        occurredPositions[str] = Pair(generatedList, currentDepth)
        return generatedList
    }

    private fun checkLine(list: List<UByte>): Int {
        return if (positions[pieceToMove.index].containsAll(list)) 1 else 0
    }

    /**
     * @param move the last move we have performed
     * @return the amount of removes we need to perform
     */
    private fun removalAmount(move: Movement): Int {
        val positionsToCheck = removeChecker[move.endIndex]!!.toList().map { it.toList() }
        var removalAmount = 0
        // we firstly create a pair cause otherwise we will lose indexes
        positionsToCheck.forEach {
            removalAmount += checkLine(it)
        }
        return removalAmount
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
            }.toList()
        }
        return positions
    }

    /**
     * @param color color of the piece
     * @return possible movements
     */
    private fun generateMoves(color: Piece): List<Movement> {
        when (gameState(color)) {
            GameState.Placement -> {
                return generatePlacementMovements(color)
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
     * @param color color of the piece
     * @return possible piece placements
     */
    private fun generatePlacementMovements(color: Piece): List<Movement> {
        if (freePieces[color.index] == 0) {
            return mutableListOf()
        }
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
        val allElements = this.positions.toList()
        val allElementsPieces = (allElements[0].map { Pair(it, Piece.GREEN) } + allElements[1].map {
            Pair(it, Piece.BLUE_)
        } + allElements[2].map { Pair(it, Piece.EMPTY) }).sortedBy { it.first }.map { it.second }
        val c = allElementsPieces.map {
            when (it) {
                Piece.BLUE_ -> {
                    BLUE_CIRCLE
                    //blue + CIRCLE + none
                }

                Piece.GREEN -> {
                    GREEN_CIRCLE
                    //green + CIRCLE + none
                }

                Piece.EMPTY -> {
                    GRAY_CIRCLE
                    //CIRCLE
                }
            }
        }
        println(
            """
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