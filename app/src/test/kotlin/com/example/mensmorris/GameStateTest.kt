package com.example.mensmorris

import com.example.mensmorris.common.gameBoard.utils.GameState
import com.example.mensmorris.positions.GameStateTestData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GameStateTest : GameStateTestData() {
    @Test
    fun `game state test 1`() {
        assertEquals(position.gameState(), GameState.Normal)
    }
}
