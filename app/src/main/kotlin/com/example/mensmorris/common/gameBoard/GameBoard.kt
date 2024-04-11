package com.example.mensmorris.common.gameBoard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import com.example.mensmorris.BUTTON_WIDTH
import com.example.mensmorris.R
import com.example.mensmorris.common.Locate
import com.example.mensmorris.common.Position
import com.example.mensmorris.common.utils.GameUtils
import com.example.mensmorris.common.utils.movesHistory
import com.example.mensmorris.common.utils.undoneMoveHistory

/**
 * Proves a useful way to draw boards
 */
class GameBoard(
    /**
     * stores current position
     */
    override var pos: MutableState<Position> = mutableStateOf(GameUtils.gameStartPosition),
    /**
     * what will happen if we click some circle
     */
    var onClick: (index: Int, func: (elementIndex: Int) -> Unit) -> Unit,
    /**
     * what we should additionally do on undo
     */
    var onUndo: () -> Unit = {}
) : GameClickHandler(pos, moveHints, selectedButton) {
    companion object {
        /**
         * stores all pieces which can be moved (used for highlighting)
         */
        private var moveHints: MutableState<List<Int>> = mutableStateOf(listOf())

        /**
         * used for storing info of the previous (valid one) clicked button
         */
        private var selectedButton = mutableStateOf<Int?>(null)
    }

    constructor(pos: Position) : this(mutableStateOf(pos), { _, _ -> }, {})

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
    fun Draw(
    ) {
        Locate(Alignment.TopCenter) {
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
                containerColor = GameUtils.colorMap(pos.value.positions[elementIndex])
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
                        pos.value = movesHistory.lastOrNull() ?: GameUtils.gameStartPosition
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
                        pos.value = movesHistory.lastOrNull() ?: GameUtils.gameStartPosition
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
}
