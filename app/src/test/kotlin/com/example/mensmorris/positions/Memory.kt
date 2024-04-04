package com.example.mensmorris.positions

import com.example.mensmorris.BLUE_
import com.example.mensmorris.EMPTY
import com.example.mensmorris.GREEN
import com.example.mensmorris.common.Position

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
