package com.example.mensmorris.game

import com.example.mensmorris.ui.Screen
import com.example.mensmorris.ui.currentScreen
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
    if (pos.gameState() == GameState.End) {
        currentScreen = Screen.EndGameScreen
    }
}
