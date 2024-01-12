/**
 * used to store movement
 * @param startIndex index of place a piece moves from
 * @param endIndex index of place a piece moves to
 */
class Movement(private val startIndex: UByte?, val endIndex: UByte?) {
    /**
     * @param pos position we have a more for
     * @return position after specified move
     */
    fun producePosition(pos: Position): Position {
        val copy = pos.copy()
        if (startIndex == null) {
            when (copy.pieceToMove) {
                Piece.GREEN -> {
                    copy.freePieces = Pair(copy.freePieces.first - 1, copy.freePieces.second)
                }

                Piece.BLUE_ -> {
                    copy.freePieces = Pair(copy.freePieces.first, copy.freePieces.second - 1)
                }

                else -> {
                    error("piece to move can't be EMPTY")
                }
            }
        } else {
            copy.positions[2U].add(startIndex)
            copy.positions[0U].remove(startIndex) || copy.positions[1U].remove(startIndex)
        }
        if (endIndex != null) {
            copy.positions[2U].remove(endIndex)
            copy.positions[copy.pieceToMove.index].add(endIndex)
        }
        return copy
    }
}

/**
 * provides a way to get an element from pair
 * fancy way
 * @param T any type
 * @param index index of the required element
 */
operator fun <T> Triple<T, T, T>.get(index: UByte): T {
    return when (index) {
        0.toUByte() -> {
            first
        }

        1.toUByte() -> {
            second
        }

        2.toUByte() -> {
            third
        }

        else -> {
            throw IllegalArgumentException("Illegal index when getting triple element")
        }
    }
}

/**
 * provides a way to get an element from pair
 * fancy way
 * @param T any type
 * @param index index of the required element
 */
operator fun <T> Pair<T, T>.get(index: UByte): T {
    return when (index) {
        0.toUByte() -> {
            first
        }

        1.toUByte() -> {
            second
        }

        else -> {
            throw IllegalArgumentException("Illegal index when getting pair element")
        }
    }
}

/**
 * in fact, there are other ways to get possible move without mapping them.
 * I just think this is the easiest and the fastest one
 */
val moveProvider: HashMap<UByte, List<UByte>> = hashMapOf(
    0.toUByte() to listOf(1U, 9U),
    1.toUByte() to listOf(0U, 2U, 4U),
    2.toUByte() to listOf(1U, 14U),
    3.toUByte() to listOf(10U, 4U),
    4.toUByte() to listOf(1U, 3U, 5U, 7U),
    5.toUByte() to listOf(4U, 13U),
    6.toUByte() to listOf(7U, 11U),
    7.toUByte() to listOf(6U, 4U, 8U),
    8.toUByte() to listOf(7U, 12U),
    9.toUByte() to listOf(0U, 10U, 21U),
    10.toUByte() to listOf(9U, 3U, 11U, 18U),
    11.toUByte() to listOf(6U, 10U, 15U),
    12.toUByte() to listOf(8U, 17U, 13U),
    13.toUByte() to listOf(5U, 12U, 14U, 20U),
    14.toUByte() to listOf(2U, 13U, 23U),
    15.toUByte() to listOf(11U, 16U),
    16.toUByte() to listOf(15U, 17U, 19U),
    17.toUByte() to listOf(12U, 16U),
    18.toUByte() to listOf(10U, 19U),
    19.toUByte() to listOf(16U, 18U, 20U),
    20.toUByte() to listOf(13U, 19U),
    21.toUByte() to listOf(9U, 22U),
    22.toUByte() to listOf(19U, 21U, 23U),
    23.toUByte() to listOf(14U, 22U)
)

/**
 * in fact, there are other ways to get possible triples without mapping them.
 * I just think this is the easiest and the fastest one
 */
val removeChecker: HashMap<UByte, List<List<UByte>>> = hashMapOf(
    0.toUByte() to listOf(listOf(1U, 2U), listOf(9U, 21U)),
    1.toUByte() to listOf(listOf(0U, 2U), listOf(4U, 7U)),
    2.toUByte() to listOf(listOf(0U, 1U), listOf(14U, 23U)),
    3.toUByte() to listOf(listOf(4U, 5U), listOf(10U, 18U)),
    4.toUByte() to listOf(listOf(1U, 7U), listOf(3U, 5U)),
    5.toUByte() to listOf(listOf(3U, 4U), listOf(13U, 20U)),
    6.toUByte() to listOf(listOf(7U, 8U), listOf(11U, 15U)),
    7.toUByte() to listOf(listOf(6U, 8U), listOf(4U, 1U)),
    8.toUByte() to listOf(listOf(6U, 7U), listOf(12U, 17U)),
    9.toUByte() to listOf(listOf(0U, 21U), listOf(10U, 11U)),
    10.toUByte() to listOf(listOf(3U, 18U), listOf(9U, 11U)),
    11.toUByte() to listOf(listOf(9U, 10U), listOf(6U, 15U)),
    12.toUByte() to listOf(listOf(8U, 17U), listOf(13U, 14U)),
    13.toUByte() to listOf(listOf(5U, 20U), listOf(12U, 14U)),
    14.toUByte() to listOf(listOf(12U, 13U), listOf(2U, 23U)),
    15.toUByte() to listOf(listOf(6U, 11U), listOf(16U, 17U)),
    16.toUByte() to listOf(listOf(15U, 17U), listOf(19U, 22U)),
    17.toUByte() to listOf(listOf(15U, 16U), listOf(8U, 12U)),
    18.toUByte() to listOf(listOf(3U, 10U), listOf(19U, 20U)),
    19.toUByte() to listOf(listOf(18U, 20U), listOf(16U, 22U)),
    20.toUByte() to listOf(listOf(18U, 19U), listOf(5U, 13U)),
    21.toUByte() to listOf(listOf(0U, 9U), listOf(22U, 23U)),
    22.toUByte() to listOf(listOf(16U, 19U), listOf(21U, 23U)),
    23.toUByte() to listOf(listOf(21U, 22U), listOf(2U, 14U))
)

/**
 * a quick way to get piece color based on it's index
 * @see Piece
 * @return piece
 */
val colorMap: HashMap<Int, Piece> = hashMapOf(
    0 to Piece.GREEN,
    1 to Piece.BLUE_,
    2 to Piece.EMPTY
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
