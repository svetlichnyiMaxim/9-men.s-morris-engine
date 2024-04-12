package com.example.mensmorris.common.gameBoard

import androidx.compose.runtime.MutableState
import com.example.mensmorris.Screen
import com.example.mensmorris.common.Movement
import com.example.mensmorris.common.Position
import com.example.mensmorris.common.moveProvider
import com.example.mensmorris.common.utils.CacheUtils
import com.example.mensmorris.common.utils.GameUtils
import com.example.mensmorris.common.utils.saveMove
import com.example.mensmorris.currentScreen

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
    private val selectedButton: MutableState<Int?>
) {

    /**
     * handles click on the pieces
     * @param elementIndex element that got clicked
     */
    fun handleClick(elementIndex: Int) {
        when (pos.value.gameState()) {
            GameUtils.GameState.Placement -> {
                if (pos.value.positions[elementIndex] == null) {
                    processMove(Movement(null, elementIndex))
                }
            }

            GameUtils.GameState.Normal -> {
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

            GameUtils.GameState.Flying -> {
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

            GameUtils.GameState.Removing -> {
                if (pos.value.positions[elementIndex] == !pos.value.pieceToMove) {
                    processMove(Movement(elementIndex, null))
                }
            }

            GameUtils.GameState.End -> {
                currentScreen.value = Screen.EndGame
            }
        }
    }

    /**
     * finds pieces we should highlight
     */
    fun handleHighLighting() {
        pos.value.generateMoves(0u, true).let { moves ->
            when (pos.value.gameState()) {
                GameUtils.GameState.Placement -> {
                    moveHints.value = moves.map { it.endIndex!! }.toMutableList()
                }

                GameUtils.GameState.Normal -> {
                    if (selectedButton.value == null) {
                        moveHints.value = moves.map { it.startIndex!! }.toMutableList()
                    } else {
                        moveHints.value =
                            moves.filter { it.startIndex == selectedButton.value }
                                .map { it.endIndex!! }
                                .toMutableList()
                    }
                }

                GameUtils.GameState.Flying -> {
                    if (selectedButton.value == null) {
                        moveHints.value = moves.map { it.startIndex!! }.toMutableList()
                    } else {
                        moveHints.value =
                            moves.filter { it.startIndex == selectedButton.value }
                                .map { it.endIndex!! }
                                .toMutableList()
                    }
                }

                GameUtils.GameState.Removing -> {
                    moveHints.value = moves.map { it.startIndex!! }.toMutableList()
                }

                GameUtils.GameState.End -> {
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
        if (pos.value.gameState() == GameUtils.GameState.End) {
            currentScreen.value = Screen.EndGame
        }
        CacheUtils.hasCacheWithDepth = false
    }
}
