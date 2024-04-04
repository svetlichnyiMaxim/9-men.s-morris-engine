package com.example.mensmorris.common.gameBoard

import androidx.compose.runtime.MutableState
import com.example.mensmorris.common.Movement
import com.example.mensmorris.common.Position
import com.example.mensmorris.Screen
import com.example.mensmorris.currentScreen
import com.example.mensmorris.common.moveProvider
import com.example.mensmorris.common.utils.AnalyzeUtils
import com.example.mensmorris.common.utils.CacheUtils
import com.example.mensmorris.common.utils.GameUtils
import com.example.mensmorris.common.utils.saveMove

/**
 * handles clicks on the board
 */
open class GameClickHandler(
    /**
     * our position
     */
    open val position: MutableState<Position>
) {
    /**
     * handles click on the pieces
     * @param elementIndex element that got clicked
     */
    fun handleClick(elementIndex: Int) {
        when (position.value.gameState()) {
            GameUtils.GameState.Placement -> {
                if (position.value.positions[elementIndex] == null) {
                    processMove(Movement(null, elementIndex))
                }
            }

            GameUtils.GameState.Normal -> {
                if (CacheUtils.selectedButton.value == null) {
                    if (position.value.positions[elementIndex] == position.value.pieceToMove) {
                        CacheUtils.selectedButton.value = elementIndex
                    }
                } else {
                    if (moveProvider[CacheUtils.selectedButton.value!!]!!.filter { endIndex ->
                            position.value.positions[endIndex] == null
                        }.contains(elementIndex)) {
                        processMove(Movement(CacheUtils.selectedButton.value, elementIndex))
                    } else {
                        GameUtils.pieceToMoveSelector(elementIndex)
                    }
                }
            }

            GameUtils.GameState.Flying -> {
                if (CacheUtils.selectedButton.value == null) {
                    if (position.value.positions[elementIndex] == position.value.pieceToMove)
                        CacheUtils.selectedButton.value = elementIndex
                } else {
                    if (position.value.positions[elementIndex] == null) {
                        processMove(Movement(CacheUtils.selectedButton.value, elementIndex))
                    } else {
                        GameUtils.pieceToMoveSelector(elementIndex)
                    }
                }
            }

            GameUtils.GameState.Removing -> {
                if (position.value.positions[elementIndex] == !position.value.pieceToMove) {
                    processMove(Movement(elementIndex, null))
                }
            }

            GameUtils.GameState.End -> {
                currentScreen.value = Screen.EndGame
            }
        }
        handleHighLighting()
    }

    /**
     * finds pieces we should highlight
     */
    private fun handleHighLighting() {
        position.value.generateMoves(0u, true).let { moves ->
            when (position.value.gameState()) {
                GameUtils.GameState.Placement -> {
                    CacheUtils.moveHints.value = moves.map { it.endIndex!! }.toMutableList()
                }

                GameUtils.GameState.Normal -> {
                    if (CacheUtils.selectedButton.value == null) {
                        CacheUtils.moveHints.value = moves.map { it.startIndex!! }.toMutableList()
                    } else {
                        CacheUtils.moveHints.value =
                            moves.filter { it.startIndex == CacheUtils.selectedButton.value }
                                .map { it.endIndex!! }
                                .toMutableList()
                    }
                }

                GameUtils.GameState.Flying -> {
                    if (CacheUtils.selectedButton.value == null) {
                        CacheUtils.moveHints.value = moves.map { it.startIndex!! }.toMutableList()
                    } else {
                        CacheUtils.moveHints.value =
                            moves.filter { it.startIndex == CacheUtils.selectedButton.value }
                                .map { it.endIndex!! }
                                .toMutableList()
                    }
                }

                GameUtils.GameState.Removing -> {
                    CacheUtils.moveHints.value = moves.map { it.startIndex!! }.toMutableList()
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
        CacheUtils.selectedButton.value = null
        position.value = move.producePosition(position.value).copy()
        AnalyzeUtils.resetAnalyze()
        saveMove(position.value)
        if (position.value.gameState() == GameUtils.GameState.End) {
            currentScreen.value = Screen.EndGame
        }
    }
}
