package com.kr8ne.mensMorris.common.gameBoard

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.GAME_END_SCREEN
import com.kr8ne.mensMorris.common.gameBoard.utils.CacheUtils
import com.kr8ne.mensMorris.common.gameBoard.utils.GameState
import com.kr8ne.mensMorris.common.gameBoard.utils.moveProvider
import com.kr8ne.mensMorris.common.utils.positionToNuke
import java.util.Stack

/**
 * handles clicks on the board
 */
open class GameClickHandler(
    /**
     * our position
     */
    open val pos: MutableState<Position>,
    /**
     * move hints
     * list of all possible places you can move
     */
    open val moveHints: MutableState<List<Int>>,
    /**
     * selected button
     * usually it is the last one clicked
     */
    private val selectedButton: MutableState<Int?>,
    /**
     * navigation controller
     */
    val navController: NavHostController?
) {
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
     * handles click on the pieces
     * @param elementIndex element that got clicked
     */
    fun handleClick(elementIndex: Int) {
        when (pos.value.gameState()) {
            GameState.Placement -> {
                if (pos.value.positions[elementIndex] == null) {
                    processMove(Movement(null, elementIndex))
                }
            }

            GameState.Normal -> {
                if (selectedButton.value == null) {
                    if (pos.value.positions[elementIndex] == pos.value.pieceToMove) {
                        selectedButton.value = elementIndex
                    }
                } else {
                    if (moveProvider[selectedButton.value!!]!!.filter { endIndex ->
                            pos.value.positions[endIndex] == null
                        }.contains(elementIndex)) {
                        processMove(Movement(selectedButton.value, elementIndex))
                    } else {
                        selectedButton.value = elementIndex
                    }
                }
            }

            GameState.Flying -> {
                if (selectedButton.value == null) {
                    if (pos.value.positions[elementIndex] == pos.value.pieceToMove)
                        selectedButton.value = elementIndex
                } else {
                    if (pos.value.positions[elementIndex] == null) {
                        processMove(Movement(selectedButton.value, elementIndex))
                    } else {
                        selectedButton.value = elementIndex
                    }
                }
            }

            GameState.Removing -> {
                if (pos.value.positions[elementIndex] == !pos.value.pieceToMove) {
                    processMove(Movement(elementIndex, null))
                }
            }

            GameState.End -> {
                Log.e("screen switching error", "tried to handle move with END game state")
            }
        }
    }

    /**
     * finds pieces we should highlight
     */
    fun handleHighLighting() {
        pos.value.generateMoves(0u, true).let { moves ->
            when (pos.value.gameState()) {
                GameState.Placement -> {
                    moveHints.value = moves.map { it.endIndex!! }.toMutableList()
                }

                GameState.Normal -> {
                    if (selectedButton.value == null) {
                        moveHints.value = moves.map { it.startIndex!! }.toMutableList()
                    } else {
                        moveHints.value = moves.filter { it.startIndex == selectedButton.value }
                            .map { it.endIndex!! }.toMutableList()
                    }
                }

                GameState.Flying -> {
                    if (selectedButton.value == null) {
                        moveHints.value = moves.map { it.startIndex!! }.toMutableList()
                    } else {
                        moveHints.value = moves.filter { it.startIndex == selectedButton.value }
                            .map { it.endIndex!! }.toMutableList()
                    }
                }

                GameState.Removing -> {
                    moveHints.value = moves.map { it.startIndex!! }.toMutableList()
                }

                GameState.End -> {
                }
            }
        }
    }

    /**
     * processes selected movement
     */
    fun processMove(move: Movement) {
        selectedButton.value = null
        pos.value = move.producePosition(pos.value).copy()
        CacheUtils.resetCachedPositions()
        saveMove(pos.value)
        if (pos.value.gameState() == GameState.End) {
            positionToNuke = pos.value
            navController?.navigate(GAME_END_SCREEN)
        }
        CacheUtils.hasCacheWithDepth = false
    }

    /**
     * saves a move we have made
     */
    private fun saveMove(pos: Position) {
        if (undoneMoveHistory.isNotEmpty()) {
            undoneMoveHistory.clear()
        }
        movesHistory.push(pos)
    }
}
