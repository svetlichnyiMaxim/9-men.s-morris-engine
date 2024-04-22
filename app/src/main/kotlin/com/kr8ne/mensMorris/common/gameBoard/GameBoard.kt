package com.kr8ne.mensMorris.common.gameBoard

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.BUTTON_WIDTH
import com.kr8ne.mensMorris.GAME_END_SCREEN
import com.kr8ne.mensMorris.R
import com.kr8ne.mensMorris.common.gameBoard.utils.CacheUtils
import com.kr8ne.mensMorris.common.gameBoard.utils.GameState
import com.kr8ne.mensMorris.common.gameBoard.utils.gameStartPosition
import com.kr8ne.mensMorris.common.gameBoard.utils.moveProvider
import com.kr8ne.mensMorris.common.utils.positionToNuke
import java.util.Stack
import kotlin.concurrent.Volatile

/**
 * Proves a useful way to draw boards
 */
class GameBoard(
    /**
     * stores current position
     */
    @Volatile
    var pos: MutableState<Position> = mutableStateOf(gameStartPosition),
    /**
     * what will happen if we click some circle
     */
    var onClick: (index: Int, func: (elementIndex: Int) -> Unit) -> Unit = { _, _ -> },
    /**
     * what we should additionally do on undo
     */
    var onUndo: () -> Unit = {},
    /**
     * stores all pieces which can be moved (used for highlighting)
     */
    private var moveHints: MutableState<List<Int>> = mutableStateOf(listOf()),
    /**
     * used for storing info of the previous (valid one) clicked button
     */
    private var selectedButton: MutableState<Int?> = mutableStateOf(null),
    /**
     * navigation controller
     */
    val navController: NavHostController?
) {
    constructor(
        pos: Position,
        onClick: (index: Int, func: (elementIndex: Int) -> Unit) -> Unit = { _, _ -> },
        onUndo: () -> Unit = {},
        moveHints: MutableState<List<Int>> = mutableStateOf(listOf()),
        selectedButton: MutableState<Int?> = mutableStateOf(null),
        navController: NavHostController?
    ) : this(mutableStateOf(pos), onClick, onUndo, moveHints, selectedButton, navController)

    private fun onClickResponse(index: Int) {
        onClick(index) {
            handleClick(it)
            handleHighLighting()
        }
    }

    /**
     * there are ways not to hard code this, but it looses a lot of readability
     */
    @Composable
    private fun DrawCircles() {
        Column(
            modifier = Modifier.size(BUTTON_WIDTH * 7, BUTTON_WIDTH * 7)
        ) {
            RowOfCircles(0, 2, 0..2)
            RowOfCircles(1, 1, 3..5)
            RowOfCircles(2, 0, 6..8)
            Box {
                RowOfCircles(0, 0, 9..11)
                RowOfCircles(4, 0, 12..14)
            }
            RowOfCircles(2, 0, 15..17)
            RowOfCircles(1, 1, 18..20)
            RowOfCircles(0, 2, 21..23)
        }
    }

    @Composable
    private fun DrawVerticalShadows() {
        Row(
            modifier = Modifier.size(BUTTON_WIDTH * 7, BUTTON_WIDTH * 7)
        ) {
            VerticalShadow(0.25f, 0.25f)
            VerticalShadow(1.25f, 1.25f)
            VerticalShadow(2.25f, 2.25f)
            Column(
                modifier = Modifier
                    .padding(
                        top = BUTTON_WIDTH * 0.25f, bottom = BUTTON_WIDTH * 0.25f
                    )
                    .height(BUTTON_WIDTH * 7)
                    .width(BUTTON_WIDTH),
                verticalArrangement = Arrangement.spacedBy(
                    BUTTON_WIDTH * 1.5f, Alignment.CenterVertically
                )
            ) {
                VerticalShadow(0f, 0f, 2.5f, 2.5f)
                VerticalShadow(0f, 0f, 2.5f, 2.5f)
            }
            VerticalShadow(2.25f, 2.25f)
            VerticalShadow(1.25f, 1.25f)
            VerticalShadow(0.25f, 0.25f)
        }
    }

    @Composable
    private fun DrawHorizontalShadows() {
        Column(
            modifier = Modifier.size(BUTTON_WIDTH * 7, BUTTON_WIDTH * 7)
        ) {
            HorizontalShadow(0.25f, 0.25f)
            HorizontalShadow(1.25f, 1.25f)
            HorizontalShadow(2.25f, 2.25f)
            Row(
                modifier = Modifier
                    .padding(
                        start = BUTTON_WIDTH * 0.25f, end = BUTTON_WIDTH * 0.25f
                    )
                    .height(BUTTON_WIDTH)
                    .width(BUTTON_WIDTH * 7),
                horizontalArrangement = Arrangement.spacedBy(
                    BUTTON_WIDTH * 1.5f, Alignment.CenterHorizontally
                )
            ) {
                HorizontalShadow(0f, 0f, 2.5f, 2.5f)
                HorizontalShadow(0f, 0f, 2.5f, 2.5f)
            }
            HorizontalShadow(2.25f, 2.25f)
            HorizontalShadow(1.25f, 1.25f)
            HorizontalShadow(0.25f, 0.25f)
        }
    }

    @Composable
    private fun VerticalShadow(
        paddingLeft: Float,
        paddingRight: Float,
        length1: Float = 7f,
        length2: Float = (7 - (paddingLeft + paddingRight))
    ) {
        Box(
            modifier = Modifier
                .height(BUTTON_WIDTH * length1)
                .width(BUTTON_WIDTH * 1f)
                .padding(
                    top = BUTTON_WIDTH * paddingLeft,
                    start = BUTTON_WIDTH * 0.25f,
                    end = BUTTON_WIDTH * 0.25f,
                    bottom = BUTTON_WIDTH * paddingRight
                )
        ) {
            Box(
                modifier = Modifier
                    .alpha(0.5f)
                    .height(BUTTON_WIDTH * length2)
                    .width(BUTTON_WIDTH * 0.5f)
                    .shadow(
                        elevation = 10.dp, shape = RoundedCornerShape(8.dp)
                    )
                    .background(Color.DarkGray)
            ) {}
        }
    }

    @Composable
    private fun HorizontalShadow(
        paddingLeft: Float,
        paddingRight: Float,
        length1: Float = 7f,
        length2: Float = (7 - (paddingLeft + paddingRight))
    ) {
        Box(
            modifier = Modifier
                .height(BUTTON_WIDTH * 1f)
                .width(BUTTON_WIDTH * length1)
                .padding(
                    top = BUTTON_WIDTH * 0.25f,
                    start = BUTTON_WIDTH * paddingLeft,
                    end = BUTTON_WIDTH * paddingRight,
                    bottom = BUTTON_WIDTH * 0.25f
                )
        ) {
            Box(
                modifier = Modifier
                    .alpha(0.5f)
                    .height(BUTTON_WIDTH * 0.5f)
                    .width(BUTTON_WIDTH * length2)
                    .shadow(
                        elevation = 10.dp, shape = RoundedCornerShape(8.dp)
                    )
                    .background(Color.DarkGray)
            ) {}
        }
    }

    /**
     * draws boards
     */
    @Composable
    fun RenderBoard(
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(), Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .padding(BUTTON_WIDTH)
                    .clip(RoundedCornerShape(15))
                    .width(BUTTON_WIDTH * 9)
                    .height(BUTTON_WIDTH * 9)
                    .background(Color(0xFF8F8F8F)),
                Alignment.Center,
            ) {
                DrawCircles()
                DrawHorizontalShadows()
                DrawVerticalShadows()
            }
        }
    }

    /**
     * draws a raw of circles
     * @param padding padding
     * @param gap between elements
     * @param range range of indexes
     */
    @Composable
    private fun RowOfCircles(
        padding: Int, gap: Int, range: IntRange
    ) {
        Row(
            modifier = Modifier.padding(start = BUTTON_WIDTH * padding),
            horizontalArrangement = Arrangement.spacedBy(
                BUTTON_WIDTH * gap, Alignment.CenterHorizontally
            ),
            Alignment.CenterVertically,
        ) {
            for (i in range) {
                CircledButton(elementIndex = i) { onClickResponse(it) }
            }
        }
    }

    /**
     * draws a circles button
     * @param elementIndex index of this circle
     * @param onClick function we execute on click
     */
    @Composable
    private fun CircledButton(elementIndex: Int, onClick: (Int) -> Unit) {
        Button(modifier = Modifier
            .alpha(
                if (moveHints.value.contains(elementIndex)) {
                    0.7f
                } else {
                    if (selectedButton.value == elementIndex) {
                        0.6f
                    } else {
                        if (pos.value.positions[elementIndex] == null) {
                            0f
                        } else 1f
                    }
                }
            )
            .size(BUTTON_WIDTH)
            .background(Color.Transparent, CircleShape),
            colors = ButtonDefaults.buttonColors(
                containerColor = when (pos.value.positions[elementIndex]) {
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
            ),
            shape = CircleShape,
            onClick = {
                onClick(elementIndex)
            }) {}
    }

    /**
     * renders undo buttons
     */
    @Composable
    fun RenderUndoRedo() {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            Alignment.BottomStart
        ) {
            Button(modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape),
                onClick = {
                    if (!movesHistory.empty()) {
                        undoneMoveHistory.push(movesHistory.peek())
                        movesHistory.pop()
                        pos.value = movesHistory.lastOrNull() ?: gameStartPosition
                        moveHints.value = arrayListOf()
                        selectedButton.value = null
                        onUndo()
                    }
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.redo_move), "undo"
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            Alignment.BottomEnd
        ) {
            Button(modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape),
                onClick = {
                    if (!undoneMoveHistory.empty()) {
                        movesHistory.push(undoneMoveHistory.peek())
                        undoneMoveHistory.pop()
                        pos.value = movesHistory.lastOrNull() ?: gameStartPosition
                        moveHints.value = arrayListOf()
                        selectedButton.value = null
                        onUndo()
                    }
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.undo_move), "redo"
                )
            }
        }
    }

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
     * handles click on the pieces
     * @param elementIndex element that got clicked
     */
    private fun handleClick(elementIndex: Int) {
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

    /**
     * renders piece counters
     */
    @Composable
    fun RenderPieceCount() {
        Box {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(BUTTON_WIDTH * if (pos.value.pieceToMove) 1.5f else 1f)
                        .background(Color.Green, CircleShape)
                        .alpha(if (pos.value.freePieces.first == 0.toUByte()) 0f else 1f),
                    Alignment.Center
                ) {
                    Text(color = Color.Blue, text = pos.value.freePieces.first.toString())
                }
            }
            Box(
                modifier = Modifier, contentAlignment = Alignment.TopEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(BUTTON_WIDTH * if (!pos.value.pieceToMove) 1.5f else 1f)
                        .background(Color.Blue, CircleShape)
                        .alpha(if (pos.value.freePieces.second == 0.toUByte()) 0f else 1f),
                    Alignment.Center
                ) {
                    Text(color = Color.Green, text = pos.value.freePieces.second.toString())
                }
            }
        }
    }
}
