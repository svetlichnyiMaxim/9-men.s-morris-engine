package com.example.mensmorris

import com.example.mensmorris.utils.GameUtils
import junit.framework.TestCase
import org.junit.Test

class GameStateTest {
    private val position = Position(
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

    @Test
    fun `game state test 1`() {
        TestCase.assertEquals(position.gameState(), GameUtils.GameState.Normal)
    }
}
