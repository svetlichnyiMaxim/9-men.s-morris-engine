package com.kr8ne.mensMorris

import com.kr8ne.mensMorris.common.gameBoard.utils.GameState
import com.kr8ne.mensMorris.positions.GameStateTestData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GameStateTest : GameStateTestData() {
    @Test
    fun `game state test 1`() {
        assertEquals(position.gameState(), GameState.Normal)
    }
}
