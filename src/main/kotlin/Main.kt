const val CIRCLE = "\uD83D\uDD35"
const val blue = "\u001B[34m"
const val green = "\u001B[32m"
const val none = "\u001B[30m"

val examplePosition = Position(
    mutableListOf(
        Piece.BLUE,
        Piece.BLUE,
        Piece.EMPTY,
        Piece.BLUE,
        Piece.GREEN,
        Piece.GREEN,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.GREEN,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.GREEN,
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
    // [34m - blue
    // [31m - red
/*    examplePosition.generatePositions(Piece.GREEN).forEach { it.display() }*/
    solve(2, Piece.BLUE, examplePosition).second.forEach {
        it.display()
        println(it.advantage(Piece.BLUE))
    }
}


/**
 * @param depth current depth
 * @color color of the piece we are finding a move for
 * @return possible positions and there evaluation
 */
fun solve(
    depth: Int, color: Piece, position: Position
): Pair<Int, MutableList<Position>> {
    // if left depth is 0
    if (depth == 0 || position.gameEnded()) {
        return Pair(position.advantage(Piece.BLUE), mutableListOf(position))
    }
    // for all possible positions we try to solve them
    val b = position.generatePositions(color).map { solve(depth - 1, color.opposite(), it) }.maxBy { it.first }
        .apply { this.second.add(position) }
    return b
}

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