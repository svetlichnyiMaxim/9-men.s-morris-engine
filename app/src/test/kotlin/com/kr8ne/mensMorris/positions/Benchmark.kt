package com.kr8ne.mensMorris.positions

import com.kr8ne.mensMorris.BLUE_
import com.kr8ne.mensMorris.EMPTY
import com.kr8ne.mensMorris.GREEN
import com.kr8ne.mensMorris.common.game.Position

open class Benchmark {
    val benchmark1 = Position(
        // @formatter:off
        arrayOf(
            EMPTY,                  EMPTY,                  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  EMPTY,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  EMPTY,                  EMPTY
        ),
        // @formatter:on
        freePieces = Pair(5u, 5u), pieceToMove = true
    )

    val benchmark2 = Position(
        // @formatter:off
        arrayOf(
            GREEN,                  EMPTY,                  EMPTY,
                    BLUE_,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  EMPTY,  EMPTY,          EMPTY,  GREEN,  EMPTY,
                            EMPTY,  EMPTY,  BLUE_,
                    EMPTY,          GREEN,          EMPTY,
            EMPTY,                  EMPTY,                  BLUE_
        ),
        // @formatter:on
        freePieces = Pair(0u, 0u), pieceToMove = true
    )
}
