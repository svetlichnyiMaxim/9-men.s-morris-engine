package com.example.mensmorris.utils

import com.example.mensmorris.Position
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
