package com.example.mensmorris.ui

import androidx.activity.compose.setContent
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.mensmorris.game.Position
import com.example.mensmorris.game.handleClick
import com.example.mensmorris.game.moveHints
import com.example.mensmorris.game.pos
import com.example.mensmorris.game.selectedButton

@Composable
inline fun Locate(alignment: Alignment, function: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(), alignment
    ) {
        function()
    }
}


@Composable
inline fun AppTheme(function: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFBDBDBD))
    ) {
        function()
    }
}

fun render(function: @Composable () -> Unit) {
    mainActivity.setContent {
        function()
    }
}

@Composable
fun DrawBoard(
    position: Position = pos, onClick: (UByte) -> Unit = { handleClick(it) }
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
                RowOfCircles(0, 2, 0..2, position, onClick)
                RowOfCircles(1, 1, 3..5, position, onClick)
                RowOfCircles(2, 0, 6..8, position, onClick)
                Box {
                    RowOfCircles(0, 0, 9..11, position, onClick)
                    RowOfCircles(4, 0, 12..14, position, onClick)
                }
                RowOfCircles(2, 0, 15..17, position, onClick)
                RowOfCircles(1, 1, 18..20, position, onClick)
                RowOfCircles(0, 2, 21..23, position, onClick)
            }
        }
    }
}

@Composable
fun RowOfCircles(
    padding: Int,
    gap: Int,
    range: IntRange,
    position: Position = pos,
    onClick: (UByte) -> Unit = { handleClick(it) }
) {
    Row(
        modifier = Modifier.padding(start = BUTTON_WIDTH * padding),
        horizontalArrangement = Arrangement.spacedBy(
            BUTTON_WIDTH * gap, Alignment.CenterHorizontally
        ),
        Alignment.CenterVertically,
    ) {
        for (i in range) {
            CircledButton(elementIndex = i, position, onClick)
        }
    }
}

@Composable
fun CircledButton(elementIndex: Int, position: Position, onClick: (UByte) -> Unit) {
    Box {
        Button(modifier = Modifier
            .alpha(
                if (moveHints.value.contains(elementIndex.toUByte())) {
                    0.7f
                } else {
                    if (selectedButton.value == elementIndex.toUByte()) 0.6f
                    else {
                        1f
                    }
                }
            )
            .size(BUTTON_WIDTH)
            .background(Color.Transparent, CircleShape),
            colors = ButtonDefaults.buttonColors(
                containerColor = position.getIndexColor(elementIndex.toUByte()).color
            ),
            shape = CircleShape,
            onClick = {
                onClick(elementIndex.toUByte())
            }) {}
    }
}