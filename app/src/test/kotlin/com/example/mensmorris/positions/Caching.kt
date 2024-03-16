package com.example.mensmorris.positions

import com.example.mensmorris.game.BLUE_
import com.example.mensmorris.game.EMPTY
import com.example.mensmorris.game.GREEN
import com.example.mensmorris.game.Position

open class Caching {
    internal val position = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  BLUE_,                  EMPTY,
                    GREEN,          EMPTY,          BLUE_,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  GREEN,  GREEN,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  BLUE_
        ),
        // @formatter:on
        pieceToMove = true
    )
}
