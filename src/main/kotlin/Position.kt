/**
 * used for storing position data
 * @param positions all pieces
 * @param freePieces pieces we can still place: first - green, second - blue
 */
data class Position(
    var positions: MutableList<Piece>, var freePieces: Pair<Int, Int> = Pair(0, 0)
) {
    /**
     * @return a copy of the current position
     */
    fun copy(): Position {
        return Position(positions.toMutableList(), freePieces)
    }

    /**
     * @param color color of pieces we search for
     * @return amount of pieces of specified color
     */
    private fun countPiece(color: Piece): Int {
        return positions.count { it == color }
    }

    /**
     * @return if the game has ended
     */
    fun gameEnded(): Piece? {
        if (countPiece(Piece.GREEN) + freePieces[Piece.GREEN.index] < 3)
            return Piece.GREEN
        if (countPiece(Piece.BLUE) + freePieces[Piece.BLUE.index] < 3)
            return Piece.BLUE
        return null
    }

    /**
     * @return advantage of pieces of current color
     */
    fun advantage(color: Piece): Int {
        return when (gameEnded()) {
            color.opposite() -> Int.MAX_VALUE
            color -> Int.MIN_VALUE
            else -> (countPiece(color) + freePieces[color.index]) - (countPiece(color.opposite()) + freePieces[color.opposite().index])
        }
    }

    /**
     * @return indexes with pieces of the needed color
     */
    private fun indexes(piece: Piece): List<Int> {
        return positions.mapIndexed { index: Int, pieceColor -> if (pieceColor == piece) index else null }
            .filterNotNull()
            .toList()
    }

    /**
     * @return possible positions we can achieve in 1 move
     */
    fun generatePositions(color: Piece): List<Position> {
        return generateMoves(color).map { Pair(it, it.producePosition(this)) }.map {
            val removalAmount = it.second.removalAmount(it.first)
            if (removalAmount == 0) listOf(it.second) else it.second.generatePositionsAfterRemoval(removalAmount, it.first.piece)
        }.flatten()
    }

    private fun generatePositionsAfterRemoval(removalAmount: Int, color: Piece): List<Position> {
        return this.generateRemovalMove(removalAmount, color)
    }

    private fun removalAmount(move: Movement): Int {
        val positionsToCheck = removeChecker[move.endIndex]!!.toList().map { it.toList() }
        // we firstly create a pair cause otherwise we will lose indexes
        val currentPosition =
            this.positions.mapIndexed { index, piece -> Pair(index, piece == move.piece) }.filter { it.second }
                .map { it.first }
        return positionsToCheck.count { currentPosition.containsAll(it) }
    }

    private fun generateRemovalMove(amount: Int, color: Piece): List<Position> {
        var positions = listOf(this)
        repeat(amount) {
            positions = positions.flatMap { position ->
                position.indexes(color.opposite())
                    .map { Movement(it, null, color.opposite()).producePosition(position) }
            }.toList()
        }
        return positions
    }

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

    private fun generateNormalMovements(color: Piece): MutableList<Movement> {
        val movements: MutableList<Movement> = mutableListOf()
        indexes(color).forEach { startIndex ->
            moveProvider[startIndex]!!.forEach { endIndex ->
                if (positions[endIndex] == Piece.EMPTY)
                    movements.add(Movement(startIndex, endIndex, color))
            }
        }
        return movements
    }

    private fun generateFlyingMovements(color: Piece): MutableList<Movement> {
        val movements: MutableList<Movement> = mutableListOf()
        indexes(color).forEach { start ->
            indexes(Piece.EMPTY).forEach { end ->
                movements.add(Movement(start, end, color))
            }
        }
        return movements
    }

    /**
     * @return possible piece placements
     */
    private fun generatePlacementMovements(color: Piece): MutableList<Movement> {
        if (freePieces[color.index] == 0) {
            return mutableListOf()
        }
        val movements: MutableList<Movement> = mutableListOf()
        indexes(Piece.EMPTY).forEach {
            movements.add(Movement(null, it, color))
        }
        return movements
    }

    private fun gameState(color: Piece): GameState {
        return when {
            (gameEnded() != null) -> {
                GameState.End
            }

            (freePieces[color.index] > 0) -> {
                GameState.Placement
            }

            (countPiece(color) == 3) -> {
                GameState.Flying
            }

            else -> GameState.Normal
        }
    }

    fun display() {
        val c = this.positions.map {
            when (it) {
                Piece.BLUE -> {
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