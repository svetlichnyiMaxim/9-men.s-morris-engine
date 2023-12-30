const val version: Double = 1.0

val emptyList = BoardPosition(
    mutableListOf(
        Position(Triple(0, 0, 0), Piece.EMPTY),
        Position(Triple(0, 0, 1), Piece.EMPTY),
        Position(Triple(0, 0, 2), Piece.EMPTY),
        Position(Triple(0, 1, 0), Piece.EMPTY),
        Position(Triple(0, 1, 1), Piece.EMPTY),
        Position(Triple(0, 1, 2), Piece.EMPTY),
        Position(Triple(0, 2, 0), Piece.EMPTY),
        Position(Triple(0, 2, 1), Piece.EMPTY),
        Position(Triple(0, 2, 2), Piece.EMPTY),
        Position(Triple(1, 0, 0), Piece.EMPTY),
        Position(Triple(1, 0, 1), Piece.NOT),
        Position(Triple(1, 0, 2), Piece.EMPTY),
        Position(Triple(1, 1, 0), Piece.EMPTY),
        Position(Triple(1, 1, 1), Piece.NOT),
        Position(Triple(1, 1, 2), Piece.EMPTY),
        Position(Triple(1, 2, 0), Piece.EMPTY),
        Position(Triple(1, 2, 1), Piece.NOT),
        Position(Triple(1, 2, 2), Piece.EMPTY),
        Position(Triple(2, 0, 0), Piece.EMPTY),
        Position(Triple(2, 0, 1), Piece.EMPTY),
        Position(Triple(2, 0, 2), Piece.EMPTY),
        Position(Triple(2, 1, 0), Piece.EMPTY),
        Position(Triple(2, 1, 1), Piece.EMPTY),
        Position(Triple(2, 1, 2), Piece.EMPTY),
        Position(Triple(2, 2, 0), Piece.EMPTY),
        Position(Triple(2, 2, 1), Piece.EMPTY),
        Position(Triple(2, 2, 2), Piece.EMPTY)
    )
)

val examplePosition = PositionWithPlaceAble(
    Pair(0, 0), BoardPosition(
        mutableListOf(
            Position(Triple(0, 0, 0), Piece.GREEN),
            Position(Triple(0, 0, 1), Piece.GREEN),
            Position(Triple(0, 0, 2), Piece.BLUE),
            Position(Triple(0, 1, 0), Piece.BLUE),
            Position(Triple(0, 1, 1), Piece.GREEN),
            Position(Triple(0, 1, 2), Piece.BLUE),
            Position(Triple(0, 2, 0), Piece.EMPTY),
            Position(Triple(0, 2, 1), Piece.EMPTY),
            Position(Triple(0, 2, 2), Piece.EMPTY),
            Position(Triple(1, 0, 0), Piece.EMPTY),
            Position(Triple(1, 0, 1), Piece.NOT),
            Position(Triple(1, 0, 2), Piece.EMPTY),
            Position(Triple(1, 1, 0), Piece.EMPTY),
            Position(Triple(1, 1, 1), Piece.NOT),
            Position(Triple(1, 1, 2), Piece.EMPTY),
            Position(Triple(1, 2, 0), Piece.EMPTY),
            Position(Triple(1, 2, 1), Piece.NOT),
            Position(Triple(1, 2, 2), Piece.EMPTY),
            Position(Triple(2, 0, 0), Piece.EMPTY),
            Position(Triple(2, 0, 1), Piece.EMPTY),
            Position(Triple(2, 0, 2), Piece.EMPTY),
            Position(Triple(2, 1, 0), Piece.EMPTY),
            Position(Triple(2, 1, 1), Piece.EMPTY),
            Position(Triple(2, 1, 2), Piece.EMPTY),
            Position(Triple(2, 2, 0), Piece.EMPTY),
            Position(Triple(2, 2, 1), Piece.EMPTY),
            Position(Triple(2, 2, 2), Piece.EMPTY)
        )
    )
)
val emptyFull = PositionWithPlaceAble(Pair(0, 0), emptyList)

fun main() {
    println("Starting engine, version - $version")
    //val data = startingHint()
    //val currentPos = data.first

    examplePosition.display()
    println("NOTE: if advantage is positive - blue are winning")
    val miniMax = solve(3, Piece.BLUE, Node(examplePosition), 3)
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
    miniMax.third?.display()
}


/**
 * @param depth current depth
 * @color color of the piece we are finding a move for
 */
fun solve(
    depth: Int,
    color: Piece,
    position: Node,
    originalDepth: Int
): Triple<Int, Node, PositionWithPlaceAble?> {
    // if left depth is 0
    if (depth == 0 || position.value.gameEnded().first) {
        return Triple(position.value.advantage(), position, null)
    }
    // decides if we are minimizing or maximizing value
    val maximizing = color == Piece.BLUE
    val lastPosition: Node = position
    if (maximizing) {
        var maxEval = Int.MIN_VALUE
        var newWinningLine: PositionWithPlaceAble? = null
        position.value.listOfPossibleMovesWithRemoval(color).forEach {
            val evaluation = solve(depth - 1, color.oppositeColor(), Node(it), originalDepth)
            lastPosition.add(evaluation.second)
            if (evaluation.first > maxEval) {
                if (originalDepth == depth)
                    newWinningLine = it
                maxEval = evaluation.first
            }
        }
        return Triple(maxEval, lastPosition, newWinningLine)
    } else {
        var minEval = Int.MAX_VALUE
        var newWinningLine: PositionWithPlaceAble? = null
        position.value.listOfPossibleMovesWithRemoval(color).forEach {
            val eval = solve(depth - 1, color.oppositeColor(), Node(it), originalDepth)
            lastPosition.add(eval.second)
            if (eval.first < minEval) {
                if (originalDepth == depth)
                    newWinningLine = it
                minEval = eval.first
            }
        }
        return Triple(minEval, lastPosition, newWinningLine)
    }
}

enum class Piece {
    GREEN, BLUE, EMPTY, NOT
}