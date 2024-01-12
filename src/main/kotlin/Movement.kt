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
            when (pos.pieceToMove) {
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
            copy.positions[2U].add(startIndex)
            copy.positions[0U].remove(startIndex) || copy.positions[1U].remove(startIndex)
        }
        if (endIndex != null) {
            copy.positions[2U].remove(endIndex)
            copy.positions[pos.pieceToMove.index].add(endIndex)
        }
        return copy
    }
}

/**
 * provides a way to get an element from pair
 * fancy way
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
            throw IllegalArgumentException()
        }
    }
}

/**
 * provides a way to get an element from pair
 * fancy way
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
            throw IllegalArgumentException()
        }
    }
}

/**
 * provides a way to get an element from pair
 * fancy way
 * @param index index of the required element
 */
operator fun Pair<UByte, UByte>.get(index: UByte): UByte {
    return when (index) {
        0.toUByte() -> {
            first
        }

        1.toUByte() -> {
            second
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
val moveProvider: Map<UByte, List<UByte>> = mapOf(
    0.toUByte() to listOf(1.toUByte(), 9.toUByte()),
    1.toUByte() to listOf(0.toUByte(), 2.toUByte(), 4.toUByte()),
    2.toUByte() to listOf(1.toUByte(), 14.toUByte()),
    3.toUByte() to listOf(10.toUByte(), 4.toUByte()),
    4.toUByte() to listOf(1.toUByte(), 3.toUByte(), 5.toUByte(), 7.toUByte()),
    5.toUByte() to listOf(4.toUByte(), 13.toUByte()),
    6.toUByte() to listOf(7.toUByte(), 11.toUByte()),
    7.toUByte() to listOf(6.toUByte(), 4.toUByte(), 8.toUByte()),
    8.toUByte() to listOf(7.toUByte(), 12.toUByte()),
    9.toUByte() to listOf(0.toUByte(), 10.toUByte(), 21.toUByte()),
    10.toUByte() to listOf(9.toUByte(), 3.toUByte(), 11.toUByte(), 18.toUByte()),
    11.toUByte() to listOf(6.toUByte(), 10.toUByte(), 15.toUByte()),
    12.toUByte() to listOf(8.toUByte(), 17.toUByte(), 13.toUByte()),
    13.toUByte() to listOf(5.toUByte(), 12.toUByte(), 14.toUByte(), 20.toUByte()),
    14.toUByte() to listOf(2.toUByte(), 13.toUByte(), 23.toUByte()),
    15.toUByte() to listOf(11.toUByte(), 16.toUByte()),
    16.toUByte() to listOf(15.toUByte(), 17.toUByte(), 19.toUByte()),
    17.toUByte() to listOf(12.toUByte(), 16.toUByte()),
    18.toUByte() to listOf(10.toUByte(), 19.toUByte()),
    19.toUByte() to listOf(16.toUByte(), 18.toUByte(), 20.toUByte()),
    20.toUByte() to listOf(13.toUByte(), 19.toUByte()),
    21.toUByte() to listOf(9.toUByte(), 22.toUByte()),
    22.toUByte() to listOf(19.toUByte(), 21.toUByte(), 23.toUByte()),
    23.toUByte() to listOf(14.toUByte(), 22.toUByte())
)

/**
 * in fact, there are other ways to get possible triples without mapping them.
 * I just think this is the easiest and the fastest one
 */
val removeChecker: Map<UByte, Pair<Pair<UByte, UByte>, Pair<UByte, UByte>>> = mapOf(
    0.toUByte() to Pair(Pair(1.toUByte(), 2.toUByte()), Pair(9.toUByte(), 21.toUByte())),
    1.toUByte() to Pair(Pair(0.toUByte(), 2.toUByte()), Pair(4.toUByte(), 7.toUByte())),
    2.toUByte() to Pair(Pair(0.toUByte(), 1.toUByte()), Pair(14.toUByte(), 23.toUByte())),
    3.toUByte() to Pair(Pair(4.toUByte(), 5.toUByte()), Pair(10.toUByte(), 18.toUByte())),
    4.toUByte() to Pair(Pair(1.toUByte(), 7.toUByte()), Pair(3.toUByte(), 5.toUByte())),
    5.toUByte() to Pair(Pair(3.toUByte(), 4.toUByte()), Pair(13.toUByte(), 20.toUByte())),
    6.toUByte() to Pair(Pair(7.toUByte(), 8.toUByte()), Pair(11.toUByte(), 15.toUByte())),
    7.toUByte() to Pair(Pair(6.toUByte(), 8.toUByte()), Pair(4.toUByte(), 1.toUByte())),
    8.toUByte() to Pair(Pair(6.toUByte(), 7.toUByte()), Pair(12.toUByte(), 17.toUByte())),
    9.toUByte() to Pair(Pair(0.toUByte(), 21.toUByte()), Pair(10.toUByte(), 11.toUByte())),
    10.toUByte() to Pair(Pair(3.toUByte(), 18.toUByte()), Pair(9.toUByte(), 11.toUByte())),
    11.toUByte() to Pair(Pair(9.toUByte(), 10.toUByte()), Pair(6.toUByte(), 15.toUByte())),
    12.toUByte() to Pair(Pair(8.toUByte(), 17.toUByte()), Pair(13.toUByte(), 14.toUByte())),
    13.toUByte() to Pair(Pair(5.toUByte(), 20.toUByte()), Pair(12.toUByte(), 14.toUByte())),
    14.toUByte() to Pair(Pair(12.toUByte(), 13.toUByte()), Pair(2.toUByte(), 23.toUByte())),
    15.toUByte() to Pair(Pair(6.toUByte(), 11.toUByte()), Pair(16.toUByte(), 17.toUByte())),
    16.toUByte() to Pair(Pair(15.toUByte(), 17.toUByte()), Pair(19.toUByte(), 22.toUByte())),
    17.toUByte() to Pair(Pair(15.toUByte(), 16.toUByte()), Pair(8.toUByte(), 12.toUByte())),
    18.toUByte() to Pair(Pair(3.toUByte(), 10.toUByte()), Pair(19.toUByte(), 20.toUByte())),
    19.toUByte() to Pair(Pair(18.toUByte(), 20.toUByte()), Pair(16.toUByte(), 22.toUByte())),
    20.toUByte() to Pair(Pair(18.toUByte(), 19.toUByte()), Pair(5.toUByte(), 13.toUByte())),
    21.toUByte() to Pair(Pair(0.toUByte(), 9.toUByte()), Pair(22.toUByte(), 23.toUByte())),
    22.toUByte() to Pair(Pair(16.toUByte(), 19.toUByte()), Pair(21.toUByte(), 23.toUByte())),
    23.toUByte() to Pair(Pair(21.toUByte(), 22.toUByte()), Pair(2.toUByte(), 14.toUByte()))
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