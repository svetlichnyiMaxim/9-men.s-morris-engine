package com.example.mensmorris.game


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
val removeChecker: HashMap<Int?, Collection<Collection<Int>>> = hashMapOf(
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

/**
 * lists all possible triples
 */
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
