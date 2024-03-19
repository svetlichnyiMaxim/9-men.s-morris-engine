package com.example.mensmorris

import com.example.mensmorris.utils.CacheUtils
import com.example.mensmorris.utils.GameUtils
import com.example.mensmorris.utils.processMove

/**
 * handles clicks on the board
 */
object GameClickHandler {
    /**
     * handles click on the pieces
     * @param elementIndex element that got clicked
     */
    fun handleClick(elementIndex: Int) {
        when (GameUtils.pos.gameState()) {
            GameUtils.GameState.Placement -> {
                if (GameUtils.pos.positions[elementIndex] == null) {
                    processMove(Movement(null, elementIndex))
                }
            }

            GameUtils.GameState.Normal -> {
                if (CacheUtils.selectedButton.value == null) {
                    if (GameUtils.pos.positions[elementIndex] == GameUtils.pos.pieceToMove) {
                        CacheUtils.selectedButton.value = elementIndex
                    }
                } else {
                    if (moveProvider[CacheUtils.selectedButton.value!!]!!.filter { endIndex ->
                            GameUtils.pos.positions[endIndex] == null
                        }.contains(elementIndex)) {
                        processMove(Movement(CacheUtils.selectedButton.value, elementIndex))
                    } else {
                        GameUtils.pieceToMoveSelector(elementIndex)
                    }
                }
            }

            GameUtils.GameState.Flying -> {
                if (CacheUtils.selectedButton.value == null) {
                    if (GameUtils.pos.positions[elementIndex] == GameUtils.pos.pieceToMove)
                        CacheUtils.selectedButton.value = elementIndex
                } else {
                    if (GameUtils.pos.positions[elementIndex] == null) {
                        processMove(Movement(CacheUtils.selectedButton.value, elementIndex))
                    } else {
                        GameUtils.pieceToMoveSelector(elementIndex)
                    }
                }
            }

            GameUtils.GameState.Removing -> {
                if (GameUtils.pos.positions[elementIndex] == !GameUtils.pos.pieceToMove) {
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
        GameUtils.pos.generateMoves(0u, true).let { moves ->
            when (GameUtils.pos.gameState()) {
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
}
