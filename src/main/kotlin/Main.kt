package main.kotlin

const val version: Double = 1.0

val emptyList = BoardPosition(
    mutableListOf(
        Position(Triple(0, 0, 0), piece.EMPTY),
        Position(Triple(0, 0, 1), piece.EMPTY),
        Position(Triple(0, 0, 2), piece.EMPTY),
        Position(Triple(0, 1, 0), piece.EMPTY),
        Position(Triple(0, 1, 1), piece.EMPTY),
        Position(Triple(0, 1, 2), piece.EMPTY),
        Position(Triple(0, 2, 0), piece.EMPTY),
        Position(Triple(0, 2, 1), piece.EMPTY),
        Position(Triple(0, 2, 2), piece.EMPTY),
        Position(Triple(1, 0, 0), piece.EMPTY),
        Position(Triple(1, 0, 1), piece.NOT),
        Position(Triple(1, 0, 2), piece.EMPTY),
        Position(Triple(1, 1, 0), piece.EMPTY),
        Position(Triple(1, 1, 1), piece.NOT),
        Position(Triple(1, 1, 2), piece.EMPTY),
        Position(Triple(1, 2, 0), piece.EMPTY),
        Position(Triple(1, 2, 1), piece.NOT),
        Position(Triple(1, 2, 2), piece.EMPTY),
        Position(Triple(2, 0, 0), piece.EMPTY),
        Position(Triple(2, 0, 1), piece.EMPTY),
        Position(Triple(2, 0, 2), piece.EMPTY),
        Position(Triple(2, 1, 0), piece.EMPTY),
        Position(Triple(2, 1, 1), piece.EMPTY),
        Position(Triple(2, 1, 2), piece.EMPTY),
        Position(Triple(2, 2, 0), piece.EMPTY),
        Position(Triple(2, 2, 1), piece.EMPTY),
        Position(Triple(2, 2, 2), piece.EMPTY)
    )
)
val emptyFull = PositionWithPlaceAble(Pair(0, 0), emptyList)

fun main() {
    println("Starting engine, version - $version")
    val data = interpretation()
    val currentPos = data.first

    println("NOTE: if advantage is positive - blue are winning")
    val depth = data.third
    val miniMax = minimax(depth, data.second, Node(currentPos), depth)
    when (miniMax.first) {
        1000 -> {
            println("You are winning")
        }

        -1000 -> {
            println("You are loosing")
        }

        else -> {
            println("You have possible advantage in ${miniMax.first} pieces")
        }
    }
    println(miniMax.first)
    miniMax.third!!.display()
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

enum class piece {
    GREEN, BLUE, EMPTY, NOT
}