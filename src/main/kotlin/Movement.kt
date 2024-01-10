/**
 * used to store movement
 * @param startIndex index of place a piece moves from
 * @param endIndex index of place a piece moves to
 * @param color color of the piece
 */
class Movement(private val startIndex: Int?, val endIndex: Int?, val color: Piece) {
    /**
     * @param pos position we have a more for
     * @return position after specified move
     */
    fun producePosition(pos: Position): Position {
        val copy = pos.copy()
        if (startIndex == null) {
            when (color) {
                Piece.GREEN -> {
                    pos.freePieces = Pair(pos.freePieces.first - 1, pos.freePieces.second)
                }

                Piece.BLUE_ -> {
                    pos.freePieces = Pair(pos.freePieces.first, pos.freePieces.second - 1)
                }

                else -> {
                    throw IllegalStateException()
                }
            }
        } else {
            copy.positions[startIndex] = Piece.EMPTY
        }
        if (endIndex != null) {
            copy.positions[endIndex] = color
        }
        return copy
    }
}


/**
 * provides a way to get an element from pair
 * fancy way
 * @param index index of the required element
 */
operator fun Pair<Int, Int>.get(index: Int): Int {
    return when (index) {
        0 -> {
            this.first
        }

        1 -> {
            this.second
        }

        else -> {
            throw IllegalArgumentException()
        }
    }
}

/**
 * in fact, there are other ways to get possible move without mapping them.
 * I just think this is the easiest and the fastest one
 */
val moveProvider: Map<Int, List<Int>> = mapOf(
    0 to listOf(1, 9),
    1 to listOf(0, 2, 4),
    2 to listOf(1, 14),
    3 to listOf(10, 4),
    4 to listOf(1, 3, 5, 7),
    5 to listOf(4, 13),
    6 to listOf(7, 11),
    7 to listOf(6, 4, 8),
    8 to listOf(7, 12),
    9 to listOf(0, 10, 21),
    10 to listOf(9, 3, 11, 18),
    11 to listOf(6, 10, 15),
    12 to listOf(8, 17, 13),
    13 to listOf(5, 12, 14, 20),
    14 to listOf(2, 13, 23),
    15 to listOf(11, 16),
    16 to listOf(15, 17, 19),
    17 to listOf(12, 16),
    18 to listOf(10, 19),
    19 to listOf(16, 18, 20),
    20 to listOf(13, 19),
    21 to listOf(9, 22),
    22 to listOf(19, 21, 23),
    23 to listOf(14, 22)
)

/**
 * in fact, there are other ways to get possible triples without mapping them.
 * I just think this is the easiest and the fastest one
 */
val removeChecker: Map<Int, Pair<Pair<Int, Int>, Pair<Int, Int>>> = mapOf(
    0 to Pair(Pair(1, 2), Pair(9, 21)),
    1 to Pair(Pair(0, 2), Pair(4, 7)),
    2 to Pair(Pair(0, 1), Pair(14, 23)),
    3 to Pair(Pair(4, 5), Pair(10, 18)),
    4 to Pair(Pair(1, 7), Pair(3, 5)),
    5 to Pair(Pair(3, 4), Pair(13, 20)),
    6 to Pair(Pair(7, 8), Pair(11, 15)),
    7 to Pair(Pair(6, 8), Pair(4, 1)),
    8 to Pair(Pair(6, 7), Pair(12, 17)),
    9 to Pair(Pair(0, 21), Pair(10, 11)),
    10 to Pair(Pair(3, 18), Pair(9, 11)),
    11 to Pair(Pair(9, 10), Pair(6, 15)),
    12 to Pair(Pair(8, 17), Pair(13, 14)),
    13 to Pair(Pair(5, 20), Pair(12, 14)),
    14 to Pair(Pair(12, 13), Pair(2, 23)),
    15 to Pair(Pair(6, 11), Pair(16, 17)),
    16 to Pair(Pair(15, 17), Pair(19, 22)),
    17 to Pair(Pair(15, 16), Pair(8, 12)),
    18 to Pair(Pair(3, 10), Pair(19, 20)),
    19 to Pair(Pair(18, 20), Pair(16, 22)),
    20 to Pair(Pair(18, 19), Pair(5, 13)),
    21 to Pair(Pair(0, 9), Pair(22, 23)),
    22 to Pair(Pair(16, 19), Pair(21, 23)),
    23 to Pair(Pair(21, 22), Pair(2, 14))
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