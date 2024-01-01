/**
 * used for storing position data
 * @param positions all pieces
 * @param freePieces pieces we can still place: first - green, second - blue
 */
data class Position(
    val positions: MutableList<Piece>, var freePieces: Pair<Int, Int> = Pair(0, 0)
) {
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
        //this.countPiece()
        TODO("Not yet implemented")
    }

    private fun removalAmount(move: Movement): Int {
        val positionsToCheck = removeChecker[move.endIndex]!!.toList().map { it.toList() }
        // we firstly create pair cause otherwise we will lose indexes
        val currentPosition =
            this.positions.mapIndexed { index, piece -> Pair(index, piece == move.piece) }.filter { it.second }
                .map { it.first }
        return positionsToCheck.count { currentPosition.containsAll(it) }
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
        when {
            (gameEnded()) -> {
                return GameState.End
            }

            (freePieces[color.index] > 0) -> {
                return GameState.Placement
            }

            else -> return GameState.Normal
        }
    }
}