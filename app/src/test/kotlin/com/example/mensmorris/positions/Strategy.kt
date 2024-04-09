package com.example.mensmorris.positions

import com.example.mensmorris.BLUE_
import com.example.mensmorris.EMPTY
import com.example.mensmorris.GREEN
import com.example.mensmorris.common.Movement
import com.example.mensmorris.common.Position

open class Strategy {
    internal val position1 = Position(
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

    internal val position2 = Position(
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
        Pair(2147483647, -2146483648), mutableListOf(
            Movement(0, null),
            Movement(null, 18)
        )
    )

    internal val position3 = Position(
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
}
