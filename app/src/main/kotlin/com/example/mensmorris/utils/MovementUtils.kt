package com.example.mensmorris.utils

import com.example.mensmorris.Movement
import com.example.mensmorris.Position
import com.example.mensmorris.utils.AnalyzeUtils.resetAnalyze
import com.example.mensmorris.utils.CacheUtils.selectedButton
import com.example.mensmorris.utils.GameUtils.pos
import com.example.mensmorris.Screen
import com.example.mensmorris.currentScreen
import java.util.Stack

/**
 * stores all movements (positions) history
 */
val movesHistory: Stack<Position> = Stack()

/**
 * stores a moves we have undone
 * resets them if we do any other move
 */
val undoneMoveHistory: Stack<Position> = Stack()

/**
 * saves a move we have made
 */
fun saveMove(pos: Position) {
    if (undoneMoveHistory.isNotEmpty()) {
        undoneMoveHistory.clear()
    }
    movesHistory.push(pos)
}

/**
 * processes selected movement
 */
fun processMove(move: Movement) {
    selectedButton.value = null
    pos = move.producePosition(pos).copy()
    resetAnalyze()
    saveMove(pos)
    if (pos.gameState() == GameUtils.GameState.End) {
        currentScreen.value = Screen.EndGame
    }
}
