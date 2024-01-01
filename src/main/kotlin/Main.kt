/*const val version: Double = 1.0

val examplePosition = Position(
    mutableListOf(
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY
    )
)

fun main() {
    println("Starting engine, version - $version")
    val miniMax = solve(3, Piece.BLUE, examplePosition, 3)
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


*//**
 * @param depth current depth
 * @color color of the piece we are finding a move for
 *//*
fun solve(
    depth: Int, color: Piece, position: Position, originalDepth: Int
): Pair<Byte, Position> {
    if (depth == 0 || position.gameEnded()) {
        return Pair(position.advantage(color), position)
    }
    // decides if we are minimizing or maximizing value
    val maximizing = color == Piece.BLUE
    if (maximizing) {
        var maxEval = Int.MIN_VALUE
        var newWinningLine: Position? = null
        position.listOfPossibleMovesWithRemoval(color).forEach {
            val evaluation = solve(depth - 1, color.oppositeColor(), it, originalDepth)
            lastPosition.add(evaluation.second)
            if (evaluation.first > maxEval) {
                if (originalDepth == depth) newWinningLine = it
                maxEval = evaluation.first
            }
        }
        return Triple(maxEval, lastPosition, newWinningLine)
    } else {
        var minEval = Int.MAX_VALUE
        var newWinningLine: Position? = null
        position.value.listOfPossibleMovesWithRemoval(color).forEach {
            val eval = solve(depth - 1, color.oppositeColor(), Node(it), originalDepth)
            lastPosition.add(eval.second)
            if (eval.first < minEval) {
                if (originalDepth == depth) newWinningLine = it
                minEval = eval.first
            }
        }
        return Triple(minEval, lastPosition, newWinningLine)
    }
}*/

/**
 * used for storing piece color
 * index is index of free pieces in Position class
 */
enum class Piece(val index: Int) {
    GREEN(0), BLUE(1), EMPTY(-1)
}

/**
 * @return opposite color
 */
fun Piece.opposite(): Piece {
    return when (this) {
        Piece.GREEN -> {
            Piece.BLUE
        }

        Piece.BLUE -> {
            Piece.GREEN
        }

        else -> {
            throw IllegalStateException()
        }
    }
}

enum class GameState {
    Placement, Normal, Flying, End
}