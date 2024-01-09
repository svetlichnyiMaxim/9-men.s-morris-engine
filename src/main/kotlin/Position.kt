/**
 * used for storing position data
 * @param positions all pieces
 * @param freePieces pieces we can still place: first - green, second - blue
 */
data class Position(
    val positions: MutableList<Piece>, var freePieces: Pair<Int, Int> = Pair(0, 0)
) {
    fun copy() = Position(positions.toMutableList(), freePieces)

    private fun countPiece(color: Piece): Int {
        return positions.count { it == color }
    }

    fun gameEnded(): Boolean {
        return (countPiece(Piece.GREEN) + freePieces[Piece.GREEN.index]) < 3 || (countPiece(Piece.BLUE) + freePieces[Piece.BLUE.index]) < 3
    }

    fun advantage(color: Piece): Int {
        return (countPiece(color) + freePieces[color.index]) - (countPiece(color.opposite()) + freePieces[color.opposite().index])
    }

    private fun indexes(piece: Piece): MutableList<Int> {
        val listOfIndexes: MutableList<Int> = mutableListOf()
        positions.forEachIndexed { index, pieceColor ->
            if (pieceColor == piece) {
                listOfIndexes.add(index)
            }
        }
        return listOfIndexes
    }

    fun generatePositions(color: Piece): MutableList<Position> {
        val allPositions: MutableList<Position> = mutableListOf()
        val positions = generateMoves(color).map { Pair(it, it.producePosition(this)) }
        positions.forEach {
            val removalAmount = it.second.removalAmount(it.first)
            allPositions.addAll(it.second.generatePositionsAfterRemoval(removalAmount, it.first.piece))
        }
        return allPositions
    }

    private fun generatePositionsAfterRemoval(removalAmount: Int, color: Piece): MutableList<Position> {
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

    fun generateRemovalMove(amount: Int, color: Piece): MutableList<Position> {
        var positions = mutableListOf(this)
        repeat(amount) {
            positions = positions.flatMap { position ->
                position.indexes(color.opposite())
                    .map { Movement(it, null, color.opposite()).producePosition(position) }
            }.toMutableList()
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
            (gameEnded()) -> {
                GameState.End
            }

            (freePieces[color.index] > 0) -> {
                GameState.Placement
            }

            (indexes(color).size == 3) -> {
                GameState.Flying
            }

            else -> GameState.Normal
        }
    }

    fun display() {
        val c = this.positions.map {
            when (it) {
                Piece.BLUE -> {
                    blue + CIRCLE
                }

                Piece.GREEN -> {
                    green + CIRCLE
                }

                Piece.EMPTY -> {
                    none + CIRCLE
                }
            } + none
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