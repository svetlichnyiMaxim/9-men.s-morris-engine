package com.example.mensmorris.positions

import com.example.mensmorris.BLUE_
import com.example.mensmorris.EMPTY
import com.example.mensmorris.GREEN
import com.example.mensmorris.common.gameBoard.Movement
import com.example.mensmorris.common.gameBoard.Position

open class Strategy {
    val position1 = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  EMPTY,                  EMPTY,
                    GREEN,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          BLUE_,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        freePieces = Pair(0u, 0u), pieceToMove = true, removalCount = 0
    )

    val correctResult1 = Pair(
        Pair(-2146483646, 2147483645), mutableListOf<Movement>()
    )

    val position2 = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  EMPTY,                  EMPTY,
                    GREEN,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          BLUE_,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        freePieces = Pair(1u, 1u), pieceToMove = true, removalCount = 0
    )

    val correctResult2 = Pair(
        Pair(2147483646, -2146483647), mutableListOf(
            Movement(0, null),
            Movement(null, 18)
        )
    )

    val position3 = Position(
        // @formatter:off
        arrayOf(
            EMPTY,                  EMPTY,                  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
                            GREEN,  EMPTY,  GREEN,
            EMPTY,  EMPTY,  BLUE_,          EMPTY,  EMPTY,  EMPTY,
                            BLUE_,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          BLUE_,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        freePieces = Pair(5u, 5u), pieceToMove = false, removalCount = 0
    )

    val correctResult3 = Pair(
        Pair(-1000, 1000), mutableListOf(
            Movement(null, 0),
            Movement(null, 7)
        )
    )

    val position4 = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  EMPTY,                  BLUE_,
                    EMPTY,          BLUE_,          EMPTY,
                            EMPTY,  BLUE_,  EMPTY,
            EMPTY,  EMPTY,  EMPTY,          GREEN,  GREEN,  GREEN,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            BLUE_,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        freePieces = Pair(0u, 0u), pieceToMove = false, removalCount = 0
    )

    val correctResult4 = Pair(
        Pair(-2146483647, 2147483646), mutableListOf(
            Movement(12, null),
            Movement(0, 1)
        )
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
}
