package com.example.mensmorris.positions

import com.example.mensmorris.game.Movement
import com.example.mensmorris.game.Position
import com.example.mensmorris.game.blue
import com.example.mensmorris.game.empty
import com.example.mensmorris.game.green

val position1 = Position(
    // @formatter:off
        mutableListOf(
            blue(),                                     empty(),                                    empty(),
                            green(),                    empty(),                    empty(),
                                        empty(),        empty(),        empty(),
            empty(),        green(),    empty(),                        empty(),    empty(),        empty(),
                                        empty(),        empty(),        empty(),
                            empty(),                    empty(),                    blue(),
            empty(),                                    empty(),                                    empty()
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
    Pair(2147483647, -2146483648), mutableListOf(
        Movement(0, null),
        Movement(null, 18)
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
        Movement(null, 0),
        Movement(null, 7)
    )
)
