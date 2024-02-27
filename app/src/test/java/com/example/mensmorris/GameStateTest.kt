package com.example.mensmorris

import com.example.mensmorris.game.GameState
import com.example.mensmorris.game.Position
import com.example.mensmorris.game.blue
import com.example.mensmorris.game.empty
import com.example.mensmorris.game.green
import junit.framework.TestCase
import org.junit.Test

class GameStateTest {
    private val position = Position(
        // @formatter:off
        mutableListOf(
            empty(),                             blue(),                                 empty(),
                        blue(),                blue(),                blue(),
                                    green(),    empty(),    empty(),
            green(),    empty(),    green(),                blue(),    empty(),        empty(),
                                    green(),    empty(),    green(),
                        empty(),                green(),                empty(),
            empty(),                            empty(),                                empty()
        ),
        // @formatter:on
        pieceToMove = true
    )

    @Test
    fun `game state test 1`() {
        TestCase.assertEquals(position.gameState(), GameState.Normal)
    }
}