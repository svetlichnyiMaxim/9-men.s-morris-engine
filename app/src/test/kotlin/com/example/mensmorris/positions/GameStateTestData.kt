package com.example.mensmorris.positions

import com.example.mensmorris.BLUE_
import com.example.mensmorris.EMPTY
import com.example.mensmorris.GREEN
import com.example.mensmorris.common.gameBoard.Position

open class GameStateTestData {
    internal val position = Position(
        // @formatter:off
        arrayOf(
            EMPTY,                  BLUE_,                  EMPTY,
                    BLUE_,          BLUE_,          BLUE_,
                            GREEN,  EMPTY,  EMPTY,
            GREEN,  EMPTY,  GREEN,          BLUE_,  EMPTY,  EMPTY,
                            GREEN,  EMPTY,  GREEN,
                    EMPTY,          GREEN,          EMPTY,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        pieceToMove = true
    )
}
