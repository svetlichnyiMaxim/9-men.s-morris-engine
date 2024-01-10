const val CIRCLE = "\uD83D\uDD35"
const val blue = "\u001B[34m"
const val green = "\u001B[32m"
const val none = "\u001B[90m"
const val BLUE_CIRCLE = "\uD83D\uDD35"
const val GREEN_CIRCLE = "\uD83D\uDFE2"
const val GRAY_CIRCLE = "⚪"

/**
 * we store occurred positions here which massively increases speed
 */
val occurredPositions: HashMap<String, Pair<List<Position>, Int>> = hashMapOf()

@Suppress("IncorrectFormatting")
val examplePosition = Position(
    mutableListOf(
        Piece.BLUE_, Piece.BLUE_, Piece.EMPTY,
        Piece.EMPTY, Piece.GREEN, Piece.GREEN,
        Piece.GREEN, Piece.EMPTY, Piece.EMPTY,
        Piece.BLUE_, Piece.GREEN, Piece.GREEN, Piece.EMPTY, Piece.EMPTY, Piece.BLUE_,
        Piece.EMPTY, Piece.GREEN, Piece.EMPTY,
        Piece.EMPTY, Piece.EMPTY, Piece.EMPTY,
        Piece.EMPTY, Piece.BLUE_, Piece.EMPTY
    ),
    pieceToMove = Piece.BLUE_
)

/*
0-----------------1-----------------2
|                 |                 |
|     3-----------4-----------5     |
|     |           |           |     |
|     |     6-----7-----8     |     |
|     |     |           |     |     |
9-----10----11          12----13----14
|     |     |           |     |     |
|     |     15----16----17    |     |
|     |           |           |     |
|     18----------19----------20    |
|                 |                 |
21----------------22----------------23
 */

/**
 * part of the program it starts from
 */
fun main() {
    //examplePosition.generatePositions(Piece.GREEN).forEach { it.display() }
    solve(5, examplePosition).second.forEach {
        it.display()
        println(it.advantage(Piece.BLUE_))
    }
}


/**
 * @param depth current depth
 * @color color of the piece we are finding a move for
 * @return possible positions and there evaluation
 */
fun solve(
    depth: Int, position: Position
): Pair<Int, MutableList<Position>> {
    if (depth == 0 || position.gameEnded() != null) {
        return Pair(position.advantage(position.pieceToMove), mutableListOf(position))
    }
    // for all possible positions, we try to solve them
    return position.generatePositions(position.pieceToMove, depth)
        .map { solve(depth - 1, it.apply { it.pieceToMove = it.pieceToMove.opposite() }) }
        .maxBy { it.second.first().advantage(position.pieceToMove) }.apply { second.add(position) }
}

/**
 * used for storing piece color
 * @param index index of free pieces in Position class
 */
enum class Piece(val index: Int) {
    /**
     * green color
     */
    GREEN(0),

    /**
     * blue color
     */
    BLUE_(1),

    /**
     * no piece is placed
     */
    EMPTY(-1)
}

/**
 * @return opposite color
 */
fun Piece.opposite(): Piece {
    return when (this) {
        Piece.GREEN -> {
            Piece.BLUE_
        }

        Piece.BLUE_ -> {
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