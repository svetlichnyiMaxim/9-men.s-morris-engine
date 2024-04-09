package com.example.mensmorris

import com.example.mensmorris.common.Position
import com.example.mensmorris.common.utils.GameUtils
import com.example.mensmorris.positions.GameState
import junit.framework.TestCase
import org.junit.Test

class GameStateTest : GameState() {
    @Test
    fun `game state test 1`() {
        TestCase.assertEquals(position.gameState(), GameUtils.GameState.Normal)
    }
}
