/**
 * used for storing position data
 * @param positions all pieces
 * @param freePieces pieces we can still place: first - green, second - blue
 */
data class Position(
    var positions: MutableList<Piece>, var freePieces: Pair<Int, Int> = Pair(0, 0), var pieceToMove: Piece
) {
    override fun toString(): String {
        return positions.joinToString { it.index.toString() } + " " + freePieces.first + freePieces.second + pieceToMove.index
    }

    /**
     * @return a copy of the current position
     */
    fun copy(): Position {
        return Position(positions.toMutableList(), freePieces, pieceToMove)
    }

    /**
     * @param color color of the piece
     * @return number of pieces with specified color
     */
    private fun countPieces(color: Piece): Int {
        return positions.count { it == color }
    }

    /**
     * @return if the game has ended
     */
    fun gameEnded(): Piece? {
        if (countPieces(Piece.GREEN) + freePieces[Piece.GREEN.index] < 3)
            return Piece.GREEN
        if (countPieces(Piece.BLUE_) + freePieces[Piece.BLUE_.index] < 3)
            return Piece.BLUE_
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
    private fun indexes(color: Piece): List<Int> {
        return positions.mapIndexed { index: Int, pieceColor -> if (pieceColor == color) index else null }
            .filterNotNull()
            .toList()
    }

    /**
     * @param color color of the piece
     * @param currentDepth the current depth we are at
     * @return possible positions we can achieve in 1 move
     */
    fun generatePositions(color: Piece, currentDepth: Int): List<Position> {
        val str = this.toString()
        occurredPositions[str]?.let {
            return it.first
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
                removalAmount,
                it.second.pieceToMove
            )
        }.flatten()
        occurredPositions[str] = Pair(generatedList, currentDepth)
        return generatedList
    }

    private fun checkLine(list: List<Int>): Int {
        list.forEach {
            if (this.positions[it] != this.pieceToMove) {
                return 0
            }
        }
        return 1
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
                position.indexes(color.opposite())
                    .map { Movement(it, null).producePosition(position) }
            }.toList()
        }
        return positions
    }

    /**
     * @param color color of the piece
     * @return possible movements
     */
    private fun generateMoves(color: Piece): MutableList<Movement> {
        when (gameState(color)) {
            GameState.Placement -> {
                return generatePlacementMovements(color)
            }

            GameState.End -> {
                return mutableListOf()
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
    private fun generateNormalMovements(color: Piece): MutableList<Movement> {
        val movements: MutableList<Movement> = mutableListOf()
        indexes(color).forEach { startIndex ->
            moveProvider[startIndex]!!.forEach { endIndex ->
                if (positions[endIndex] == Piece.EMPTY)
                    movements.add(Movement(startIndex, endIndex))
            }
        }
        return movements
    }

    /**
     * @param color color of the piece
     * @return all possible flying movements
     */
    private fun generateFlyingMovements(color: Piece): MutableList<Movement> {
        val movements: MutableList<Movement> = mutableListOf()
        indexes(color).forEach { start ->
            indexes(Piece.EMPTY).forEach { end ->
                movements.add(Movement(start, end))
            }
        }
        return movements
    }

    /**
     * @param color color of the piece
     * @return possible piece placements
     */
    private fun generatePlacementMovements(color: Piece): MutableList<Movement> {
        if (freePieces[color.index] == 0) {
            return mutableListOf()
        }
        val movements: MutableList<Movement> = mutableListOf()
        indexes(Piece.EMPTY).forEach {
            movements.add(Movement(null, it))
        }
        return movements
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
        val c = this.positions.map {
            when (it) {
                Piece.BLUE_ -> {
                    //BLUE_CIRCLE
                    blue + CIRCLE + none
                }

                Piece.GREEN -> {
                    //GREEN_CIRCLE
                    green + CIRCLE + none
                }

                Piece.EMPTY -> {
                    //GRAY_CIRCLE
                    CIRCLE
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