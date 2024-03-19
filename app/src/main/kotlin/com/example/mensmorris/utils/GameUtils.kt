package com.example.mensmorris.utils

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.Color
import com.example.mensmorris.EMPTY
import com.example.mensmorris.Position
import com.example.mensmorris.utils.AnalyzeUtils.resetAnalyze
import com.example.mensmorris.utils.CacheUtils.gamePosition
import com.example.mensmorris.utils.CacheUtils.selectedButton

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
}
