const val CIRCLE = "\uD83D\uDD35"
const val blue = "\u001B[34m"
const val green = "\u001B[32m"
const val none = "\u001B[90m"
const val BLUE_CIRCLE = "\uD83D\uDD35"
const val GREEN_CIRCLE = "\uD83D\uDFE2"
const val GRAY_CIRCLE = "⚪"

val examplePosition = Position(
    mutableListOf(
        Piece.BLUE,
        Piece.BLUE,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.GREEN,
        Piece.GREEN,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.EMPTY,
        Piece.BLUE,
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
        Piece.BLUE,
        Piece.EMPTY,
        Piece.EMPTY
    )
)

fun main() {
    //examplePosition.generatePositions(Piece.GREEN).forEach { it.display() }
    examplePosition.display()
    solve(3, Piece.BLUE, examplePosition).second.forEach {
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
    if (depth == 0 || position.gameEnded() != null) {
        return Pair(position.advantage(color), mutableListOf(position))
    }
    // for all possible positions we try to solve them
    return position.generatePositions(color).map { solve(depth - 1, color.opposite(), it) }
        .maxBy { it.second.first().advantage(color) }.apply { second.add(position) }
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

/**
 * used for storing game state
 */
enum class GameState {
    /**
     * game starting part, we simply place pieces
     */
    Placement,

    /**
     * normal part of the game
     */
    Normal,

    /**
     * part of the game where pieces can fly
     */
    Flying,

    /**
     * if game has ended xd
     */
    End
}