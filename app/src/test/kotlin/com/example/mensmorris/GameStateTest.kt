package com.example.mensmorris

import com.example.mensmorris.common.gameBoard.utils.GameState
import com.example.mensmorris.positions.GameStateTestData
import junit.framework.TestCase
import org.junit.Test

class GameStateTest : GameStateTestData() {
    @Test
    fun `game state test 1`() {
        TestCase.assertEquals(position.gameState(), GameState.Normal)
    }
}
