package com.example.mensmorris.game

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.Color
import com.example.mensmorris.game.CacheUtils.gamePosition
import com.example.mensmorris.game.CacheUtils.moveHints
import com.example.mensmorris.game.CacheUtils.selectedButton
import com.example.mensmorris.ui.Screen
import com.example.mensmorris.ui.currentScreen
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

/**
 * provides useful utils for game
 */
object GameUtils {
    /**
     * a default game start position
     */
    val gameStartPosition = Position(
        // @formatter:off
    arrayOf(
        EMPTY,                  EMPTY,                  EMPTY,
                EMPTY,          EMPTY,          EMPTY,
                        EMPTY,  EMPTY,  EMPTY,
        EMPTY,  EMPTY,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                        EMPTY,  EMPTY,  EMPTY,
                EMPTY,          EMPTY,          EMPTY,
        EMPTY,                  EMPTY,                  EMPTY
    ),
    // @formatter:on
        Pair(8u, 8u), pieceToMove = true
    )

    /**
     * @return color we are using to draw this piece
     * @param piece pieces
     */
    fun colorMap(piece: Boolean?): Color {
        return when (piece) {
            null -> {
                Color.Black
            }

            true -> {
                Color.Green
            }

            false -> {
                Color.Blue
            }
        }
    }

    /**
     * used for storing game state
     */
    enum class GameState {
        /**
         * game starting part, we simply place pieces
         */
        Placement,

        /**
         * normal part of the game
         */
        Normal,

        /**
         * part of the game where pieces can fly
         */
        Flying,

        /**
         * if game has ended xd
         */
        End,

        /**
         * if you are removing a piece
         */
        Removing
    }

    /**
     * handles click on the pieces
     * @param elementIndex element that got clicked
     */
    fun handleClick(elementIndex: Int) {
        when (pos.gameState()) {
            GameState.Placement -> {
                if (pos.positions[elementIndex] == null) {
                    processMove(Movement(null, elementIndex))
                }
            }

            GameState.Normal -> {
                if (selectedButton.value == null) {
                    if (pos.positions[elementIndex] == pos.pieceToMove) {
                        selectedButton.value = elementIndex
                    }
                } else {
                    if (moveProvider[selectedButton.value!!]!!.filter { endIndex ->
                            pos.positions[endIndex] == null
                        }.contains(elementIndex)) {
                        processMove(Movement(selectedButton.value, elementIndex))
                    } else {
                        pieceToMoveSelector(elementIndex)
                    }
                }
            }

            GameState.Flying -> {
                if (selectedButton.value == null) {
                    if (pos.positions[elementIndex] == pos.pieceToMove) selectedButton.value =
                        elementIndex
                } else {
                    if (pos.positions[elementIndex] == null) {
                        processMove(Movement(selectedButton.value, elementIndex))
                    } else {
                        pieceToMoveSelector(elementIndex)
                    }
                }
            }

            GameState.Removing -> {
                if (pos.positions[elementIndex] == !pos.pieceToMove) {
                    processMove(Movement(elementIndex, null))
                }
            }

            GameState.End -> {
                currentScreen = Screen.EndGameScreen
            }
        }
        handleHighLighting()
    }

    /**
     * finds pieces we should highlight
     */
    fun handleHighLighting() {
        pos.generateMoves(0u, true).let { moves ->
            when (pos.gameState()) {
                GameState.Placement -> {
                    moveHints.value = moves.map { it.endIndex!! }.toMutableList()
                }

                GameState.Normal -> {
                    if (selectedButton.value == null) {
                        moveHints.value = moves.map { it.startIndex!! }.toMutableList()
                    } else {
                        moveHints.value =
                            moves.filter { it.startIndex == selectedButton.value }
                                .map { it.endIndex!! }
                                .toMutableList()
                    }
                }

                GameState.Flying -> {
                    if (selectedButton.value == null) {
                        moveHints.value = moves.map { it.startIndex!! }.toMutableList()
                    } else {
                        moveHints.value =
                            moves.filter { it.startIndex == selectedButton.value }
                                .map { it.endIndex!! }
                                .toMutableList()
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
     * handles selection of the piece to move
     * @param elementIndex tested candidate
     */
    fun pieceToMoveSelector(elementIndex: Int) {
        if (pos.positions[elementIndex] != null) {
            selectedButton.value = elementIndex
        } else {
            selectedButton.value = null
        }
    }


    /**
     * provides a quick access to game position value
     */
    var pos
        inline get() = gamePosition.value
        inline set(newPos) {
            gamePosition.value = newPos
        }

    /**
     * depth our engine works at
     * @note >= 7 causes OOM
     */
    var depth = mutableIntStateOf(3)
        set(value) {
            resetAnalyze()
            field = value
        }

    /**
     * used for storing our analyze coroutine
     * gets force-stopped when no longer needed
     */
    var solvingJob: Job? = null

    /**
     * used for storing our analyze coroutine
     * gets force-stopped when no longer needed
     */
    var botJob: Job? = null

    /**
     * force stops bot
     * TODO: make sure we always shut it down
     */
    fun stopBot() {
        runBlocking {
            botJob?.cancel()
            solvingJob?.cancel()
        }
    }
}