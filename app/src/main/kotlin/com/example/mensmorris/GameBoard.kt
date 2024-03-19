package com.example.mensmorris

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.mensmorris.utils.CacheUtils
import com.example.mensmorris.utils.GameUtils
import com.example.mensmorris.utils.movesHistory
import com.example.mensmorris.utils.undoneMoveHistory
import com.example.mensmorris.GameClickHandler.handleClick

/**
 * Proves a useful way to draw boards
 */
class GameBoard(
    /**
     * stores current position
     */
    var position: Position = GameUtils.pos,
    /**
     * what will happen if we click some circle
     */
    var onClick: (Int) -> Unit = { handleClick(it) },
    /**
     * what we should additionally do on undo
     */
    var onUndo: () -> Unit
) {
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
                Column {
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
        }
    }

    /**
     * draws a raw of circles
     * @param padding padding
     * @param gap between elements
     * @param range range of indexes
     */
    @Composable
    fun RowOfCircles(
        padding: Int,
        gap: Int,
        range: IntRange
    ) {
        Row(
            modifier = Modifier.padding(start = BUTTON_WIDTH * padding),
            horizontalArrangement = Arrangement.spacedBy(
                BUTTON_WIDTH * gap, Alignment.CenterHorizontally
            ),
            Alignment.CenterVertically,
        ) {
            for (i in range) {
                CircledButton(elementIndex = i, onClick)
            }
        }
    }

    /**
     * draws a circles button
     * @param elementIndex index of this circle
     * @param onClick function we execute on click
     */
    @Composable
    fun CircledButton(elementIndex: Int, onClick: (Int) -> Unit) {
        Box {
            Button(modifier = Modifier
                .alpha(
                    if (CacheUtils.moveHints.value.contains(elementIndex)) {
                        0.7f
                    } else {
                        if (CacheUtils.selectedButton.value == elementIndex) {
                            0.6f
                        } else {
                            1f
                        }
                    }
                )
                .size(BUTTON_WIDTH)
                .background(Color.Transparent, CircleShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GameUtils.colorMap(position.positions[elementIndex])
                ),
                shape = CircleShape,
                onClick = {
                    onClick(elementIndex)
                }) {}
        }
    }

    /**
     * renders undo buttons
     */
    @Composable
    fun RenderUndoRedo() {
        Locate(Alignment.BottomStart) {
            Button(
                modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape),
                onClick = {
                    if (!movesHistory.empty()) {
                        undoneMoveHistory.push(movesHistory.peek())
                        movesHistory.pop()
                        GameUtils.pos = movesHistory.lastOrNull() ?: GameUtils.gameStartPosition
                        CacheUtils.moveHints.value = arrayListOf()
                        CacheUtils.selectedButton.value = null
                        onUndo()
                    }
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.forward), "undo"
                )
            }
        }
        Locate(Alignment.BottomEnd) {
            Button(
                modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape),
                onClick = {
                    if (!undoneMoveHistory.empty()) {
                        movesHistory.push(undoneMoveHistory.peek())
                        undoneMoveHistory.pop()
                        GameUtils.pos = movesHistory.lastOrNull() ?: GameUtils.gameStartPosition
                        CacheUtils.moveHints.value = arrayListOf()
                        CacheUtils.selectedButton.value = null
                        onUndo()
                    }
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.back), "redo"
                )
            }
        }
    }
}
