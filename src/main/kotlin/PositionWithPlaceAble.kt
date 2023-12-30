import kotlin.math.abs

class PositionWithPlaceAble(private var placeAble: Pair<Int, Int>, private val pos: BoardPosition) {
    private fun staticCopy(): PositionWithPlaceAble {
        return PositionWithPlaceAble(
            placeAble, BoardPosition(pos.collection.toMutableList())
        )
    }

    fun gameEnded(): Pair<Boolean, piece> {
        if (countPieces(piece.BLUE) + placeAble.first < 3 || listOfPossibleMoves(
                piece.BLUE
            ).isEmpty()
        ) return Pair(true, piece.BLUE)
        if (countPieces(piece.GREEN) + placeAble.second < 3 || listOfPossibleMoves(
                piece.GREEN
            ).isEmpty()
        ) return Pair(true, piece.GREEN)
        return Pair(false, piece.EMPTY)
    }

    fun advantage(): Int {
        if (gameEnded().first) {
            if (gameEnded().second == piece.GREEN) {
                return 1000
            } else {
                return -1000
            }
        }
        return (countPieces(piece.BLUE) + placeAble.first) - (countPieces(piece.GREEN) + placeAble.second)
    }

    fun listOfPossibleMovesWithRemoval(
        color: piece
    ): MutableList<PositionWithPlaceAble> {
        val list = mutableListOf<PositionWithPlaceAble>()
        applyEveryMove(color).forEach {
            val removalAmount = it.first.removalsAfterTheMove(it.second, color)
            when (removalAmount) {
                1 -> {
                    list.addAll(it.first.applyRemoves(it.first.getPossibleRemoveMoves(color)))
                }

                2 -> {
                    it.first.applyRemoves(it.first.getPossibleRemoveMoves(color)).forEach { pos ->
                        list.addAll(pos.applyRemoves(pos.getPossibleRemoveMoves(color)))
                    }
                }

                0 -> {
                    list.add(it.first)
                }
            }
        }
        return list
    }


    private fun applyRemoves(
        list: MutableList<Pair<Position, Position?>>
    ): MutableList<PositionWithPlaceAble> {
        val list1 = mutableListOf<PositionWithPlaceAble>()
        list.forEach {
            val copy = staticCopy()
            val index = copy.findPosIndex(it.first.xyz)
            copy.pos.collection[index] = Position(it.first.xyz, piece.EMPTY)
            list1.add(copy)
        }
        return list1
    }

    private fun listOfPossibleMoves(color: piece): MutableList<Pair<Position?, Position>> {
        val placeAble = placeAble

        val available = when (color) {
            piece.BLUE -> {
                placeAble.first
            }

            piece.GREEN -> {
                placeAble.second
            }

            else -> {
                throw IllegalArgumentException()
            }
        }
        val yourPieces = countPieces(color)
        when {
            available > 0 -> {
                return getGameStartMoves()
            }

            yourPieces == 3 -> {
                return getFlyPoses(color)
            }

            // Normal moves
            yourPieces > 3 -> {
                return getNormalMoves(color)
            }
        }
        // The game has ended
        return mutableListOf()
    }

    private fun applyEveryMove(
        pieceColor: piece
    ): MutableList<Pair<PositionWithPlaceAble, Triple<Int, Int, Int>>> {
        val listOfAll: MutableList<Pair<PositionWithPlaceAble, Triple<Int, Int, Int>>> = mutableListOf()
        listOfPossibleMoves(pieceColor).forEach {
            val lastMove: Triple<Int, Int, Int> = it.second.xyz
            val copy = staticCopy()
            if (it.first == null) {
                val index = findPosIndex(it.second.xyz)
                copy.pos.collection[index] = Position(it.second.xyz, pieceColor)
                when (pieceColor) {
                    piece.BLUE -> {
                        copy.placeAble = Pair(copy.placeAble.first - 1, copy.placeAble.second)
                    }

                    piece.GREEN -> {
                        copy.placeAble = Pair(copy.placeAble.first, copy.placeAble.second - 1)
                    }

                    else -> {
                        throw IllegalArgumentException()
                    }
                }
            } else {
                val index = findPosIndex(it.first!!.xyz)
                copy.pos.collection[index] = Position(it.first!!.xyz, piece.EMPTY)
                val index1 = findPosIndex(it.second.xyz)
                copy.pos.collection[index1] = Position(it.second.xyz, pieceColor)
            }
            listOfAll.add(Pair(copy, lastMove))
        }
        return listOfAll
    }

    /**
     * tries to display position in a proper way
     * green - \u001b[32m
     * blue - \u001b[34m
     */
    fun display() {
        for (y in 0..2) {
            print(" ".repeat(6 * y))
            for (z in 0..2) {
                val piece1 = findPiece(Triple(z, y, 0))
                processColor(piece1)
                print(" ".repeat(6 * (2 - y)))
            }
            println()
        }
        for (y in 0..2) {
            val piece1 = findPiece(Triple(0, y, 1))
            processColor(piece1)
        }
        print(" ".repeat(6))
        for (y in 2 downTo 0) {
            val piece1 = findPiece(Triple(2, y, 1))
            processColor(piece1)
        }
        println()
        for (y in 2 downTo 0) {
            print(" ".repeat(6 * y))
            for (x in 0..2) {
                val piece1 = findPiece(Triple(x, y, 2))
                processColor(piece1)
                print(" ".repeat(6 * (2 - y)))
            }
            println()
        }
        println("\u001b[90m list of available - (${placeAble.first}, ${placeAble.second})")
        println()
    }


    /**
     * tries to display position in a proper way
     * green - \u001b[32m
     * blue - \u001b[34m
     */
    fun hint() {
        for (y in 0..2) {
            print(" ".repeat(6 * y))
            for (z in 0..2) {
                val piece1 = findPosIndex(Triple(z, y, 0))
                processLength(piece1)
                print(" ".repeat(6 * (2 - y)))
            }
            println()
        }
        for (y in 0..2) {
            val piece1 = findPosIndex(Triple(0, y, 1))
            processLength(piece1)
        }
        print(" ".repeat(6))
        for (y in 2 downTo 0) {
            val piece1 = findPosIndex(Triple(2, y, 1))
            processLength(piece1)
        }
        println()
        for (y in 2 downTo 0) {
            print(" ".repeat(6 * y))
            for (x in 0..2) {
                val piece1 = findPosIndex(Triple(x, y, 2))
                processLength(piece1)
                print(" ".repeat(6 * (2 - y)))
            }
            println()
        }
        println()
    }


    private fun getFlyPoses(color: piece): MutableList<Pair<Position?, Position>> {
        val flyingMoves = mutableListOf<Pair<Position?, Position>>()
        listOfPieces(color).forEach { currPiece ->
            listOfPieces(piece.EMPTY).forEach { newPos ->
                flyingMoves.add(Pair(currPiece, newPos))
            }
        }
        return flyingMoves
    }

    /**
     * @return possible moves if the game is in the start stage
     */
    private fun getGameStartMoves(): MutableList<Pair<Position?, Position>> {
        return listOfPieces(piece.EMPTY).map { Pair(null, it) }.toMutableList()
    }

    /**
     * @return possible positions if game is in the normal stage
     */
    private fun getNormalMoves(color: piece): MutableList<Pair<Position?, Position>> {
        val normalMoves = mutableListOf<Pair<Position?, Position>>()
        // piece we move
        listOfPieces(color).forEach { piece ->
            // position we can move piece to
            possibleMoves(piece).forEach { newPosition ->
                normalMoves.add(Pair(piece, newPosition))
            }
        }
        return normalMoves
    }

    private fun getPossibleRemoveMoves(color: piece): MutableList<Pair<Position, Position?>> {
        val removeMoves = mutableListOf<Pair<Position, Position?>>()
        val list = listOfPieces(color.oppositeColor()).filter {
            removalsAfterTheMove(
                it.xyz, color.oppositeColor()
            ) == 0
        }
        if (list.isNotEmpty()) {
            // it isn't allowed to remove pieces if there any other moves
            list.forEach {
                removeMoves.add(Pair(it, null))
            }
        } else {
            listOfPieces(color.oppositeColor()).forEach {
                removeMoves.add(Pair(it, null))
            }
        }
        return removeMoves
    }


    /**
     * @return if there are any 3 in line
     */
    private fun removalsAfterTheMove(
        lastMove: Triple<Int, Int, Int>, color: piece
    ): Int {
        var removeCount = 0
        if (lastMove.first == 1 || lastMove.third == 1) {
            // this means that we can try vertical line
            if (hasValidVerticalLine(lastMove, color)) removeCount++
            // Checks horizontal line
            if (PositionWithPlaceAble(Pair(0, 0), pos).getNormalMoves(color).filter {
                    it.second.xyz.second == lastMove.second && findPiece(
                        it.second.xyz
                    ) == color
                }.size == 3) removeCount++
        } else {
            if (checkLineX(lastMove, color) || checkLineY(lastMove, color)) {
                removeCount++
            }
        }
        return removeCount
    }

    private fun checkLineX(lastMove: Triple<Int, Int, Int>, color: piece): Boolean {
        for (i in 0..2) {
            if (findPiece(Triple(i, lastMove.second, lastMove.third)) != color) return false
        }
        return true
    }

    private fun checkLineY(lastMove: Triple<Int, Int, Int>, color: piece): Boolean {
        for (i in 0..2) {
            if (findPiece(Triple(lastMove.first, lastMove.second, i)) != color) {
                return false
            }
        }
        return true
    }

    private fun hasValidVerticalLine(
        pos: Triple<Int, Int, Int>, color: piece
    ): Boolean {
        for (i in 0..2) {
            if (findPiece(Triple(pos.first, i, pos.third)) != color) return false
        }
        return true
    }

    private fun findPiece(xyz: Triple<Int, Int, Int>): piece {
        return getPos(xyz).piece
    }

    private fun getPos(xyz: Triple<Int, Int, Int>): Position {
        return pos.collection[xyz.third + xyz.second * 3 + xyz.first * 9]
    }

    /**
     * gets position index
     * some magic is happening here
     */
    private fun findPosIndex(xyz: Triple<Int, Int, Int>): Int {
        return xyz.third + xyz.second * 3 + xyz.first * 9
    }

    /**
     * @return number of pieces with provided color
     */
    private fun countPieces(type: piece): Int {
        return pos.collection.count { it.piece == type }
    }

    /**
     * @return list of pieces matching criteria
     */
    private fun listOfPieces(type: piece): List<Position> {
        return pos.collection.filter { it.piece == type }
    }

    private fun possibleMoves(piecePos: Position): List<Position> {
        return pos.collection.filter {
            it.piece == piece.EMPTY &&
                    // horizontal
                    ((abs(it.xyz.first - piecePos.xyz.first) + abs(
                        it.xyz.third - piecePos.xyz.third
                    ) == 1 && it.xyz.second == piecePos.xyz.second))
                    // vertical
                    || (abs(it.xyz.second - piecePos.xyz.second) == 1 && abs(it.xyz.first - piecePos.xyz.first) + abs(
                it.xyz.third - piecePos.xyz.third
            ) == 0 && (it.xyz.first == 1 || it.xyz.third == 1))
        }
    }
}