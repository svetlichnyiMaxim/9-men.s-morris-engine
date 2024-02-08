package com.example.mensmorris.game

/**
 * used to store movement
 * @param startIndex index of place a piece moves from
 * @param endIndex index of place a piece moves to
 */
class Movement(val startIndex: Int?, val endIndex: Int?) {
    /**
     * @param pos position we have a more for
     * @return position after specified move
     */
    fun producePosition(pos: Position): Position {
        val copy = pos.copy()
        if (startIndex == null) {
            when (copy.pieceToMove) {
                true -> {
                    copy.freePieces =
                        Pair((copy.freePieces.first - 1u).toUByte(), copy.freePieces.second)
                }

                false -> {
                    copy.freePieces =
                        Pair(copy.freePieces.first, (copy.freePieces.second - 1u).toUByte())
                }
            }
        } else {
            // removed
            if (copy.positions[startIndex].isGreen!!) {
                copy.greenPiecesAmount--
            } else {
                copy.bluePiecesAmount--
            }
            copy.positions[startIndex].isGreen = null
            copy.removalCount--
        }
        if (endIndex != null) {
            copy.positions[endIndex].isGreen = copy.pieceToMove
        }
        if (endIndex != null)
            copy.removalCount = copy.removalAmount(this)

        if (copy.removalCount == 0.toUByte()) {
            copy.pieceToMove = !copy.pieceToMove
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
operator fun <T> Triple<T, T, T>.get(index: Int): T {
    return when (index) {
        0 -> {
            first
        }

        1 -> {
            second
        }

        2 -> {
            third
        }

        else -> {
            throw IllegalArgumentException("Illegal index when getting triple element")
        }
    }
}

/**
 * in fact, there are other ways to get possible move without mapping them.
 * I just think this is the easiest and the fastest one
 */
val moveProvider: HashMap<Int, List<Int>> = hashMapOf(
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
    19 to listOf(16, 18, 20, 22),
    20 to listOf(13, 19),
    21 to listOf(9, 22),
    22 to listOf(19, 21, 23),
    23 to listOf(14, 22)
)

/**
 * in fact, there are other ways to get possible triples without mapping them.
 * I just think this is the easiest and the fastest one
 */
val removeChecker: HashMap<Int, List<List<Int>>> = hashMapOf(
    0 to listOf(listOf(1, 2), listOf(9, 21)),
    1 to listOf(listOf(0, 2), listOf(4, 7)),
    2 to listOf(listOf(0, 1), listOf(14, 23)),
    3 to listOf(listOf(4, 5), listOf(10, 18)),
    4 to listOf(listOf(1, 7), listOf(3, 5)),
    5 to listOf(listOf(3, 4), listOf(13, 20)),
    6 to listOf(listOf(7, 8), listOf(11, 15)),
    7 to listOf(listOf(6, 8), listOf(4, 1)),
    8 to listOf(listOf(6, 7), listOf(12, 17)),
    9 to listOf(listOf(0, 21), listOf(10, 11)),
    10 to listOf(listOf(3, 18), listOf(9, 11)),
    11 to listOf(listOf(9, 10), listOf(6, 15)),
    12 to listOf(listOf(8, 17), listOf(13, 14)),
    13 to listOf(listOf(5, 20), listOf(12, 14)),
    14 to listOf(listOf(12, 13), listOf(2, 23)),
    15 to listOf(listOf(6, 11), listOf(16, 17)),
    16 to listOf(listOf(15, 17), listOf(19, 22)),
    17 to listOf(listOf(15, 16), listOf(8, 12)),
    18 to listOf(listOf(3, 10), listOf(19, 20)),
    19 to listOf(listOf(18, 20), listOf(16, 22)),
    20 to listOf(listOf(18, 19), listOf(5, 13)),
    21 to listOf(listOf(0, 9), listOf(22, 23)),
    22 to listOf(listOf(16, 19), listOf(21, 23)),
    23 to listOf(listOf(21, 22), listOf(2, 14))
)

val triplesMap: List<List<Int>> = listOf(
    listOf(0, 1, 2),
    listOf(3, 4, 5),
    listOf(6, 7, 8),
    listOf(9, 10, 11),
    listOf(12, 13, 14),
    listOf(15, 16, 17),
    listOf(18, 19, 20),
    listOf(21, 22, 23),
    listOf(0, 9, 21),
    listOf(3, 10, 18),
    listOf(6, 11, 15),
    listOf(1, 4, 7),
    listOf(16, 19, 22),
    listOf(5, 13, 20),
    listOf(2, 14, 23)
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
