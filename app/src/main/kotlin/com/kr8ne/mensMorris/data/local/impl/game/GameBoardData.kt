package com.kr8ne.mensMorris.data.local.impl.game

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.GAME_END_SCREEN
import com.kr8ne.mensMorris.GameState
import com.kr8ne.mensMorris.Position
import com.kr8ne.mensMorris.cache.Cache
import com.kr8ne.mensMorris.common.positionToNuke
import com.kr8ne.mensMorris.data.local.interfaces.DataI
import com.kr8ne.mensMorris.gameStartPosition
import com.kr8ne.mensMorris.move.Movement
import com.kr8ne.mensMorris.move.moveProvider
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Stack

/**
 * data for game board
 */
class GameBoardData(
    /**
     * stores current position
     */
    var pos: MutableStateFlow<Position> = MutableStateFlow(gameStartPosition),
    /**
     * stores all pieces which can be moved (used for highlighting)
     */
    val moveHints: MutableStateFlow<List<Int>> = MutableStateFlow(listOf()),
    /**
     * what we should execute on undo
     */
    val onUndo: () -> Unit = {},
    /**
     * what we should execute on redo
     */
    val onRedo: () -> Unit = {},
    /**
     * what will happen if we click some circle
     */
    var onClick: GameBoardData.(index: Int) -> Unit = {},
    /**
     * used for storing info of the previous (valid one) clicked button
     */
    val selectedButton: MutableState<Int?> = mutableStateOf(null),
    /**
     * navigation controller
     */
    val navController: NavHostController?
) : DataI() {

    /**
     * stores all movements (positions) history
     */
    private val movesHistory: Stack<Position> = Stack()

    /**
     * stores a moves we have undone
     * resets them if we do any other move
     */
    private val undoneMoveHistory: Stack<Position> = Stack()

    /**
     * undoes the last move
     */
    var handleUndo = {
        if (!movesHistory.empty()) {
            undoneMoveHistory.push(movesHistory.peek())
            movesHistory.pop()
            pos.value = movesHistory.lastOrNull() ?: gameStartPosition
            Cache.resetCacheDepth()
            moveHints.value = arrayListOf()
            selectedButton.value = null
            onUndo()
        }
    }

    /**
     * handles redo (re applies moves we have undone)
     */
    var handleRedo = {
        if (!undoneMoveHistory.empty()) {
            movesHistory.push(undoneMoveHistory.peek())
            undoneMoveHistory.pop()
            pos.value = movesHistory.lastOrNull() ?: gameStartPosition
            selectedButton.value = null
            Cache.resetCacheDepth()
            moveHints.value = arrayListOf()
            onRedo()
        }
    }

    /**
     * processes selected movement
     */
    fun processMove(move: Movement) {
        pos.value = move.producePosition(pos.value).copy()
        selectedButton.value = null
        Cache.resetCacheDepth()
        saveMove(pos.value)
        if (pos.value.gameState() == GameState.End) {
            positionToNuke = pos.value
            navController?.navigate(GAME_END_SCREEN)
        }
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

    /**
     * gets movement produced by user click
     */
    @Suppress("ReturnCount")
    fun getMovement(elementIndex: Int): Movement? {
        when (pos.value.gameState()) {
            GameState.Placement -> {
                if (pos.value.positions[elementIndex] == null) {
                    return Movement(null, elementIndex)
                }
            }

            GameState.Normal -> {
                if (selectedButton.value != null) {
                    if (moveProvider[selectedButton.value!!]!!.filter { endIndex ->
                            pos.value.positions[endIndex] == null
                        }.contains(elementIndex)) {
                        return Movement(selectedButton.value, elementIndex)
                    }
                }
            }

            GameState.Flying -> {
                if (selectedButton.value != null) {
                    if (pos.value.positions[elementIndex] == null) {
                        return Movement(selectedButton.value, elementIndex)
                    }
                }
            }

            GameState.Removing -> {
                if (pos.value.positions[elementIndex] == !pos.value.pieceToMove) {
                    return Movement(elementIndex, null)
                }
            }

            GameState.End -> {
            }
        }
        return null
    }

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
}
