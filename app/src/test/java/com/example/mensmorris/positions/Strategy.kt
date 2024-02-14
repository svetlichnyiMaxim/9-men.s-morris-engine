package com.example.mensmorris.positions

import com.example.mensmorris.game.Position
import com.example.mensmorris.game.blue
import com.example.mensmorris.game.empty
import com.example.mensmorris.game.green

val position1 = Position(
    // @formatter:off
        mutableListOf(
            blue(),                                     empty(),                                     empty(),
            green(),                    empty(),                    empty(),
            empty(),        empty(),        empty(),
            empty(),        green(),    empty(),                        empty(),    empty(),        empty(),
            empty(),        empty(),        empty(),
            empty(),                    empty(),                    blue(),
            empty(),                                    empty(),                                    empty()
        ),
        // @formatter:on
    freePieces = Pair(1u, 1u), pieceToMove = true, removalCount = 0u
)
val correctResult1 = Pair(
    Pair(2147483647, -2147483648), mutableListOf(
        Position(
            mutableListOf(
                empty(),
                empty(),
                empty(),
                green(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                green(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                green(),
                empty(),
                blue(),
                empty(),
                empty(),
                empty()
            ), freePieces = Pair(0u, 1u), pieceToMove = false, removalCount = 0u
        ), Position(
            mutableListOf(
                blue(),
                empty(),
                empty(),
                green(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                green(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                green(),
                empty(),
                blue(),
                empty(),
                empty(),
                empty()
            ), freePieces = Pair(0u, 1u), pieceToMove = true, removalCount = 1u
        ), Position(
            mutableListOf(
                blue(),
                empty(),
                empty(),
                green(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                green(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                blue(),
                empty(),
                empty(),
                empty()
            ), freePieces = Pair(1u, 1u), pieceToMove = true, removalCount = 0u
        )
    )
)


val position2 = Position(
    // @formatter:off
    mutableListOf(
        blue(),                                     empty(),                                     empty(),
        green(),                    empty(),                    empty(),
        empty(),        empty(),        empty(),
        empty(),        green(),    empty(),                        empty(),    empty(),        empty(),
        empty(),        empty(),        empty(),
        empty(),                    empty(),                    blue(),
        empty(),                                    empty(),                                    empty()
    ),
    // @formatter:on
    freePieces = Pair(1u, 1u), pieceToMove = true, removalCount = 0u
)
val correctResult2 = Pair(
    Pair(2147483647, -2147483648), mutableListOf(
        Position(
            mutableListOf(
                empty(),
                empty(),
                empty(),
                green(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                green(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                green(),
                empty(),
                blue(),
                empty(),
                empty(),
                empty()
            ), freePieces = Pair(0u, 1u), pieceToMove = false, removalCount = 0u
        ),
        Position(
            mutableListOf(
                blue(),
                empty(),
                empty(),
                green(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                green(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                green(),
                empty(),
                blue(),
                empty(),
                empty(),
                empty()
            ), freePieces = Pair(0u, 1u), pieceToMove = true, removalCount = 1u
        ),
        Position(
            mutableListOf(
                blue(),
                empty(),
                empty(),
                green(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                green(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                blue(),
                empty(),
                empty(),
                empty()
            ), freePieces = Pair(1u, 1u), pieceToMove = true, removalCount = 0u
        ),
    )
)


val position3 = Position(
    // @formatter:off
    mutableListOf(
        empty(),                                empty(),                                    empty(),
        empty(),                    empty(),                    empty(),
        green(),        empty(),        green(),
        empty(),    empty(),    blue(),                         empty(),    empty(),        empty(),
        blue(),         empty(),        empty(),
        empty(),                    empty(),                    blue(),
        empty(),                                empty(),                                    empty()
    ),
    // @formatter:on
    freePieces = Pair(5u, 5u), pieceToMove = false, removalCount = 0u
)
val correctResult3 = Pair(
    Pair(-1000, 1000), mutableListOf(

        Position(
            mutableListOf(
                green(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                green(),
                blue(),
                green(),
                empty(),
                empty(),
                blue(),
                empty(),
                empty(),
                empty(),
                blue(),
                empty(),
                empty(),
                empty(),
                empty(),
                blue(),
                empty(),
                empty(),
                empty()
            ), freePieces = Pair(4u, 4u), pieceToMove = false, removalCount = 0u
        ),
        Position(
            mutableListOf(
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                green(),
                blue(),
                green(),
                empty(),
                empty(),
                blue(),
                empty(),
                empty(),
                empty(),
                blue(),
                empty(),
                empty(),
                empty(),
                empty(),
                blue(),
                empty(),
                empty(),
                empty()
            ), freePieces = Pair(5u, 4u), pieceToMove = true, removalCount = 0u
        ),
        Position(
            mutableListOf(
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                empty(),
                green(),
                empty(),
                green(),
                empty(),
                empty(),
                blue(),
                empty(),
                empty(),
                empty(),
                blue(),
                empty(),
                empty(),
                empty(),
                empty(),
                blue(),
                empty(),
                empty(),
                empty()
            ), freePieces = Pair(5u, 5u), pieceToMove = false, removalCount = 0u
        ),
    )
)
