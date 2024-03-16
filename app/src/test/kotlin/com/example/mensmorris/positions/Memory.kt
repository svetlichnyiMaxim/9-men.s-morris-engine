package com.example.mensmorris.positions

import com.example.mensmorris.game.BLUE_
import com.example.mensmorris.game.EMPTY
import com.example.mensmorris.game.GREEN
import com.example.mensmorris.game.Position

open class Memory {
    internal val wonPosition = Position(
        // @formatter:off
        arrayOf(
            EMPTY,                  EMPTY,                  BLUE_,
            EMPTY,          EMPTY,          EMPTY,
            EMPTY,  EMPTY,  BLUE_,
            EMPTY,  EMPTY,  GREEN,          EMPTY,  EMPTY,  EMPTY,
            GREEN,  EMPTY,  EMPTY,
            EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  GREEN
        ),
        // @formatter:on
        freePieces = Pair(0u, 1u),
        pieceToMove = true
    )
}
