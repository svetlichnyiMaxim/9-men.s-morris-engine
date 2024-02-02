package com.example.mensmorris.game

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.example.mensmorris.ui.Screen
import com.example.mensmorris.ui.currentScreen
import com.example.mensmorris.ui.screens.saveMove
import kotlinx.coroutines.Job
import kotlin.coroutines.cancellation.CancellationException
import kotlin.math.abs

/**
 * a default game start position
 */
val gameStartPosition = Position(
    Triple(
        (mutableListOf<UByte>() as MutableCollection<UByte>),
        mutableListOf(),
        mutableListOf(
            0u,
            1u,
            2u,
            3u,
            4u,
            5u,
            6u,
            7u,
            8u,
            9u,
            10u,
            11u,
            12u,
            13u,
            14u,
            15u,
            16u,
            17u,
            18u,
            19u,
            20u,
            21u,
            22u,
            23u
        )
    ), Pair(8u, 8u), pieceToMove = Piece.BLUE_
)

/**
 * we store occurred positions here which massively increases speed
 */
val occurredPositions: HashMap<String, Pair<List<Position>, UByte>> = hashMapOf()

/**
 * used for storing piece color
 * @param index index of free pieces in Position class
 * @param color of the piece
 */
enum class Piece(val index: UByte, val color: Color) {
    /**
     * green color
     */
    GREEN(0U, Color.Green),

    /**
     * blue color
     */
    BLUE_(1U, Color.Blue),

    /**
     * no piece is placed
     */
    EMPTY(2U, Color.Black)
}

/**
 * @return opposite color
 */
fun Piece.opposite(): Piece {
    return colorMap[abs(1 - this.index.toInt())]!!
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
fun handleClick(elementIndex: UByte) {
    var producedMove: Movement? = null
    when (pos.gameState()) {
        GameState.Placement -> {
            if (pos.getIndexColor(elementIndex) == Piece.EMPTY) {
                producedMove = Movement(null, elementIndex)
            }
        }

        GameState.Normal -> {
            if (selectedButton.value == null) {
                if (pos.getIndexColor(elementIndex) == pos.pieceToMove) selectedButton.value =
                    elementIndex
            } else {
                if (moveProvider[selectedButton.value!!]!!.filter { endIndex ->
                        pos.positions[2U].contains(endIndex)
                    }.contains(elementIndex)) {
                    producedMove = Movement(selectedButton.value, elementIndex)
                    selectedButton.value = null
                } else {
                    pieceToMoveSelector(elementIndex)
                }
            }
        }

        GameState.Flying -> {
            if (selectedButton.value == null) {
                if (pos.getIndexColor(elementIndex) == pos.pieceToMove) selectedButton.value =
                    elementIndex
            } else {
                if (pos.getIndexColor(elementIndex) == Piece.EMPTY) {
                    producedMove = Movement(selectedButton.value, elementIndex)
                    selectedButton.value = null
                } else {
                    pieceToMoveSelector(elementIndex)
                }
            }
        }

        GameState.Removing -> {
            if (pos.getIndexColor(elementIndex) == pos.pieceToMove.opposite()) {
                producedMove = Movement(elementIndex, null)
            }
        }

        GameState.End -> {
            currentScreen = Screen.EndGame
        }
    }
    producedMove?.let {
        gamePosition.value = it.producePosition(pos)
        resetAnalyze()
        saveMove(pos)
        resetCachedPositions()
        if (pos.gameState() == GameState.End) {
            currentScreen = Screen.EndGame
        }
    }
    handleHighLighting()
}

/**
 * finds pieces we should highlight
 */
fun handleHighLighting() {
    pos.generateMoves().let { moves ->
        when (pos.gameState()) {
            GameState.Placement -> {
                moveHints.value = moves.map { it.endIndex!! }.toMutableList()
            }

            GameState.Normal -> {
                if (selectedButton.value == null) {
                    moveHints.value = moves.map { it.startIndex!! }.toMutableList()
                } else {
                    moveHints.value =
                        moves.filter { it.startIndex == selectedButton.value }.map { it.endIndex!! }
                            .toMutableList()
                }
            }

            GameState.Flying -> {
                if (selectedButton.value == null) {
                    moveHints.value = moves.map { it.startIndex!! }.toMutableList()
                } else {
                    moveHints.value =
                        moves.filter { it.startIndex == selectedButton.value }.map { it.endIndex!! }
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
fun pieceToMoveSelector(elementIndex: UByte) {
    if (pos.getIndexColor(elementIndex) != Piece.EMPTY) {
        selectedButton.value = elementIndex
    } else {
        selectedButton.value = null
    }
}

/**
 * hides analyze gui and delete it's result
 */
fun resetAnalyze() {
    solveResult.value = mutableListOf()
    if (solving != null) {
        try {
            solving!!.cancel()
        } catch (_: CancellationException) {
        }
    }
}

/**
 * used to check if need to reset our cache or it already is
 */
var hasCache = false

/**
 * resets all cached positions depth, which prevents engine from skipping important moves which have
 * occurred in previous analyzes
 */
fun resetCachedPositions() {
    if (!hasCache) {
        return
    }
    occurredPositions.forEach {
        occurredPositions[it.key] = Pair(it.value.first, 0u)
    }
    hasCache = false
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
 * used for storing our game analyzes result
 */
var solveResult = mutableStateOf<MutableList<Position>>(mutableListOf())

/**
 * stores current game position
 */
var gamePosition = mutableStateOf(gameStartPosition)

/**
 * stores all pieces which can be moved (used for highlighting)
 */
var moveHints = mutableStateOf(mutableListOf<UByte>())

/**
 * used for storing info of the previous (valid one) clicked button
 */
var selectedButton = mutableStateOf<UByte?>(null)

/**
 * depth our engine works at
 * @note >= 5 causes OOM
 */
var depth = mutableIntStateOf(3)

/**
 * used for storing our analyze coroutine
 * gets force-stopped when no longer needed
 */
var solving: Job? = null
