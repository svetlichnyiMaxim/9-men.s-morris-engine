package main.kotlin

import kotlin.math.abs

fun PositionWithPlaceAble.staticCopy(): PositionWithPlaceAble {
    return PositionWithPlaceAble(
        this.placeAble, BoardPosition(this.pos.collection.toMutableList())
    )
}

fun PositionWithPlaceAble.gameEnded(): Pair<Boolean, piece> {
    if (this.count(piece.BLUE) + this.placeAble.first < 3 || this.listOfPossibleMoves(
            piece.BLUE
        ).isEmpty()
    ) return Pair(true, piece.BLUE)
    if (this.count(piece.GREEN) + this.placeAble.second < 3 || this.listOfPossibleMoves(
            piece.GREEN
        ).isEmpty()
    ) return Pair(true, piece.GREEN)
    return Pair(false, piece.EMPTY)
}

fun PositionWithPlaceAble.advantage(): Int {
    if (this.gameEnded().first) {
        if (this.gameEnded().second == piece.GREEN) {
            return 1000
        } else {
            return -1000
        }
    }
    return (count(piece.BLUE) + this.placeAble.first) - (count(piece.GREEN) + this.placeAble.second)
}

fun minimax(
    depth: Int,
    color: piece,
    position: Node,
    originalDepth: Int
): Triple<Int, Node, PositionWithPlaceAble?> {
    if (depth == 0 || position.value.gameEnded().first) {
        return Triple(position.value.advantage(), position, null)
    }

    val maximizingPlayer = color == piece.BLUE
    val lastPosition: Node = position
    if (maximizingPlayer) {
        var maxEval = Int.MIN_VALUE
        var newWinningLine: PositionWithPlaceAble? = null
        position.value.listOfPossibleMovesWithRemoval(color).forEach {
            val eval = minimax(depth - 1, color.oppositeColor(), Node(it), originalDepth)
            lastPosition.add(eval.second)
            if (eval.first > maxEval) {
                if (originalDepth == depth)
                    newWinningLine = it
                maxEval = eval.first
            }
        }
        return Triple(maxEval, lastPosition, newWinningLine)
    } else {
        var minEval = Int.MIN_VALUE
        var newWinningLine: PositionWithPlaceAble? = null
        position.value.listOfPossibleMovesWithRemoval(color).forEach {
            val eval = minimax(depth - 1, color.oppositeColor(), Node(it), originalDepth)
            lastPosition.add(eval.second)
            if (eval.first > minEval) {
                if (originalDepth == depth)
                    newWinningLine = it
                minEval = eval.first
            }
        }
        return Triple(minEval, lastPosition, newWinningLine)
    }
}

fun PositionWithPlaceAble.listOfPossibleMovesWithRemoval(
    color: piece
): MutableList<PositionWithPlaceAble> {
    val list = mutableListOf<PositionWithPlaceAble>()
    this.applyEveryMove(color).forEach {
        val removalAmount = it.first.checkConsequences(it.second, color)
        when (removalAmount) {
            1 -> {
                list.addAll(it.first.applyRemoves(it.first.getPossibleRemovePoses(color)))
            }

            2 -> {
                it.first.applyRemoves(it.first.getPossibleRemovePoses(color)).forEach { pos ->
                    list.addAll(pos.applyRemoves(pos.getPossibleRemovePoses(color)))
                }
            }

            0 -> {
                list.add(it.first)
            }
        }
    }
    return list
}


fun PositionWithPlaceAble.applyRemoves(
    list: MutableList<Pair<Position, Position?>>
): MutableList<PositionWithPlaceAble> {
    val list1 = mutableListOf<PositionWithPlaceAble>()
    list.forEach {
        val copy = this.staticCopy()
        val index = copy.findPosIndex(it.first.xyz)
        copy.pos.collection[index] = Position(it.first.xyz, piece.EMPTY)
        list1.add(copy)
    }
    return list1
}

fun PositionWithPlaceAble.listOfPossibleMoves(
    color: piece
): MutableList<Pair<Position?, Position>> {
    if (color == piece.EMPTY) throw IllegalArgumentException("WTF")
    val placeAble = this.placeAble

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
    val yourPieces = count(color)
    if (available > 0) {
        return this.getGameStartPoses()
    }
    if (yourPieces == 3) {
        return this.getFlyPoses(color)
    }

    // Normal moves
    if (yourPieces > 3) {
        return this.getNormalMoves(color)
    }

    // Game has ended
    return mutableListOf()
}

fun PositionWithPlaceAble.applyEveryMove(
    piece1: piece
): MutableList<Pair<PositionWithPlaceAble, Triple<Int, Int, Int>>> {
    val listOfAll: MutableList<Pair<PositionWithPlaceAble, Triple<Int, Int, Int>>> = mutableListOf()
    listOfPossibleMoves(piece1).forEach {
        val lastMove: Triple<Int, Int, Int> = it.second.xyz
        val copy = this.staticCopy()
        if (it.first == null) {
            val index = findPosIndex(it.second.xyz)
            copy.pos.collection[index] = Position(it.second.xyz, piece1)
            when (piece1) {
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
            copy.pos.collection[index1] = Position(it.second.xyz, piece1)
        }
        listOfAll.add(Pair(copy, lastMove))
    }
    return listOfAll
}

fun PositionWithPlaceAble.display() {
    // its purpose is to scare people, who try to read this
    // GREEN
    // \u001b[32m
    // BLUE
    // \u001b[34m
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
    println("\u001b[90m list of available - (${this.placeAble.first}, ${this.placeAble.second})")
    println()
}


fun processLength(piece1: Int) {
    val dif = 5 - piece1.toString().length
    print("${piece1}${" ".repeat(dif)} ")
}

fun PositionWithPlaceAble.display1() {
    // its purpose is to scare people, who try to read this
    // GREEN
    // \u001b[32m
    // BLUE
    // \u001b[34m
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
    println("\u001b[90m list of available - (${this.placeAble.first}, ${this.placeAble.second})")
    println()
}

fun processColor(piece1: piece) {
    val blue = "\u001b[34m"
    val nothing = "\u001b[90m"
    val green = "\u001b[32m"
    var else1 = ""
    else1 += when (piece1) {
        piece.BLUE -> {
            blue
        }

        piece.GREEN -> {
            green
        }

        else -> {
            nothing
        }
    }
    val postfix = if (piece1 == piece.BLUE) " " else ""
    print("$else1${piece1.name}$postfix ")
}

fun PositionWithPlaceAble.getFlyPoses(color: piece): MutableList<Pair<Position?, Position>> {
    val list4 = mutableListOf<Pair<Position?, Position>>()
    this.listOf1(color).forEach { currPiece ->
        this.listOf1(piece.EMPTY).forEach { newPos ->
            list4.add(Pair(currPiece, newPos))
        }
    }
    return list4
}

fun PositionWithPlaceAble.getGameStartPoses(): MutableList<Pair<Position?, Position>> {
    val list4 = mutableListOf<Pair<Position?, Position>>()
    this.listOf1(piece.EMPTY).forEach { newPos ->
        list4.add(Pair(null, newPos))
    }
    return list4
}

fun PositionWithPlaceAble.getNormalMoves(color: piece): MutableList<Pair<Position?, Position>> {
    val test2 = mutableListOf<Pair<Position?, Position>>()
    this.listOf1(color).forEach { currPiece ->
        this.possibleMoves(currPiece).forEach { newPos ->
            test2.add(Pair(currPiece, newPos))
        }
    }
    return test2
}

fun PositionWithPlaceAble.getPossibleRemovePoses(color: piece): MutableList<Pair<Position, Position?>> {
    val list4 = mutableListOf<Pair<Position, Position?>>()
    val list = this.listOf1(color.oppositeColor()).filter { this.checkConsequences(it.xyz, color.oppositeColor()) == 0 }
    if (list.isNotEmpty()) {
        // Нельзя убирать фигуры из построенных мельницы, если это возможно
        list.forEach {
            list4.add(Pair(it, null))
        }
    } else {
        this.listOf1(color.oppositeColor()).forEach {
            list4.add(Pair(it, null))
        }
    }
    return list4
}


fun PositionWithPlaceAble.checkConsequences(
    lastMove: Triple<Int, Int, Int>, color: piece
): Int {
    var removeCount = 0
    if (lastMove.first == 1 || lastMove.third == 1) {
        // this means that we can try vertical line
        if (hasValidVerticalLine(lastMove, color)) removeCount++
        // Checks horizontal line
        if (PositionWithPlaceAble(Pair(0, 0), this.pos).getNormalMoves(color).filter {
                it.second.xyz.second == lastMove.second && findPiece(
                    it.second.xyz
                ) == color
            }.size == 3) removeCount++
    } else {
        if (checkLine(lastMove, color)) removeCount++
        if (checkLine2(lastMove, color)) {
            removeCount++
        }
    }
    return removeCount
}

fun PositionWithPlaceAble.checkLine(lastMove: Triple<Int, Int, Int>, color: piece): Boolean {
    for (i in 0..2) {
        if (findPiece(Triple(i, lastMove.second, lastMove.third)) != color) return false
    }
    return true
}

fun PositionWithPlaceAble.checkLine2(lastMove: Triple<Int, Int, Int>, color: piece): Boolean {
    for (i in 0..2) {
        if (findPiece(Triple(lastMove.first, lastMove.second, i)) != color) {
            return false
        }
    }
    return true
}

fun PositionWithPlaceAble.hasValidVerticalLine(
    pos: Triple<Int, Int, Int>, color: piece
): Boolean {
    for (i in 0..2) {
        if (this.findPiece(Triple(pos.first, i, pos.third)) != color) return false
    }
    return true
}

fun PositionWithPlaceAble.findPiece(xyz: Triple<Int, Int, Int>): piece {
    return this.getPos(xyz).piece
}

fun PositionWithPlaceAble.getPos(xyz: Triple<Int, Int, Int>): Position {
    return this.pos.collection[xyz.third + xyz.second * 3 + xyz.first * 9]
}

fun PositionWithPlaceAble.findPosIndex(xyz: Triple<Int, Int, Int>): Int {
    return xyz.third + xyz.second * 3 + xyz.first * 9
}

fun PositionWithPlaceAble.count(type: piece): Int {
    return this.pos.collection.count { it.piece == type }
}

fun PositionWithPlaceAble.listOf1(type: piece): List<Position> {
    return this.pos.collection.filter { it.piece == type }
}

fun PositionWithPlaceAble.possibleMoves(piecePos: Position): List<Position> {
    return this.pos.collection.filter {
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