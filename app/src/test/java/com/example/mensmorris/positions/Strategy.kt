package com.example.mensmorris.positions

import com.example.mensmorris.game.BLUE_
import com.example.mensmorris.game.Movement
import com.example.mensmorris.game.Position
import com.example.mensmorris.game.EMPTY
import com.example.mensmorris.game.GREEN

val position1 = Position(
    // @formatter:off
        mutableListOf(
            BLUE_,                                     EMPTY,                                    EMPTY,
                            GREEN,                    EMPTY,                    EMPTY,
                                        EMPTY,        EMPTY,        EMPTY,
            EMPTY,        GREEN,    EMPTY,                        EMPTY,    EMPTY,        EMPTY,
                                        EMPTY,        EMPTY,        EMPTY,
                            EMPTY,                    EMPTY,                    BLUE_,
            EMPTY,                                    EMPTY,                                    EMPTY
        ),
        // @formatter:on
    freePieces = Pair(0u, 0u), pieceToMove = true, removalCount = 0u
)
val correctResult1 = Pair(
    Pair(-2146483648, 2147483647), mutableListOf<Movement>()
)


val position2 = Position(
    // @formatter:off
    mutableListOf(
        BLUE_,                                     EMPTY,                                     EMPTY,
                        GREEN,                    EMPTY,                    EMPTY,
                                    EMPTY,        EMPTY,        EMPTY,
        EMPTY,        GREEN,    EMPTY,                        EMPTY,    EMPTY,        EMPTY,
                                    EMPTY,        EMPTY,        EMPTY,
                        EMPTY,                    EMPTY,                    BLUE_,
        EMPTY,                                    EMPTY,                                    EMPTY
    ),
    // @formatter:on
    freePieces = Pair(1u, 1u), pieceToMove = true, removalCount = 0u
)
val correctResult2 = Pair(
    Pair(2147483647, -2146483648), mutableListOf(
        Movement(0, null),
        Movement(null, 18)
    )
)


val position3 = Position(
    // @formatter:off
    mutableListOf(
        EMPTY,                                EMPTY,                                    EMPTY,
        EMPTY,                    EMPTY,                    EMPTY,
        GREEN,        EMPTY,        GREEN,
        EMPTY,    EMPTY,    BLUE_,                         EMPTY,    EMPTY,        EMPTY,
        BLUE_,         EMPTY,        EMPTY,
        EMPTY,                    EMPTY,                    BLUE_,
        EMPTY,                                EMPTY,                                    EMPTY
    ),
    // @formatter:on
    freePieces = Pair(5u, 5u), pieceToMove = false, removalCount = 0u
)
val correctResult3 = Pair(
    Pair(-1000, 1000), mutableListOf(
        Movement(null, 0),
        Movement(null, 7)
    )
)
