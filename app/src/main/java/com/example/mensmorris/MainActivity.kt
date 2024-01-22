package com.example.mensmorris

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp


val BUTTON_WIDTH = 35.dp
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainPage()
            }
        }
    }

    @Composable
    private inline fun Locate(alignment: Alignment, function: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            alignment
        ) {
            function()
        }
    }


    @Composable
    private inline fun AppTheme(function: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFBDBDBD))
        ) {
            function()
        }
    }

    private val pos
        inline get() = gamePosition.value!!
    private var gameStartPosition = Position(
        mutableListOf(
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.BLUE_,
            Piece.EMPTY,
            Piece.GREEN,
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.EMPTY,
            Piece.EMPTY
        ), Pair(4u, 4u), pieceToMove = Piece.BLUE_
    )

    private var gamePosition = mutableStateOf<Position?>(null)
    private var gameStateRender = mutableStateOf<Boolean?>(null)
    private var selectedButton = mutableStateOf<UByte?>(null)

    @Composable
    fun gameEnd() {
        AppTheme {
            Text("Game has ended")
            Box(
                modifier = Modifier
                    .padding(
                        0.dp,
                        BUTTON_WIDTH * 12,
                        0.dp,
                        0.dp
                    )
                    .fillMaxSize(),
                Alignment.Center
            ) {
                Button(
                    onClick = {
                        setContent {
                            AppTheme {
                                MainPage()
                            }
                        }
                    }
                ) {
                    Text("Reset")
                }
            }
        }
    }
    @Composable
    fun DrawBoard() {
        gamePosition = remember { mutableStateOf(gameStartPosition) }
        gameStateRender = remember { mutableStateOf(false) }
        if (gameStateRender.value == true) {
            setContent {
                gameEnd()
            }
        }
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

    @Composable
    fun RowOfCircles(padding: Int, gap: Int, range: IntRange) {
        Row(
            modifier = Modifier.padding(start = BUTTON_WIDTH * padding),
            horizontalArrangement = Arrangement.spacedBy(
                BUTTON_WIDTH * gap,
                Alignment.CenterHorizontally
            ),
            Alignment.CenterVertically,
        ) {
            for (i in range) {
                CircledButton(elementIndex = i)
            }
        }
    }

    @Composable
    fun CircledButton(elementIndex: Int) {
        Box {
            Button(modifier = Modifier
                .alpha(
                    if (selectedButton.value == elementIndex.toUByte()) 0.5f
                    else {
                        1f
                    }
                )
                .size(BUTTON_WIDTH)
                .background(Color.Transparent, CircleShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = pos.getIndexColor(elementIndex.toUByte()).color
                ),
                shape = CircleShape,
                onClick = {
                    handleClick(elementIndex.toUByte())
                }) {}
        }
    }

    private fun handleClick(elementIndex: UByte) {
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
                gameStateRender.value = true
            }
        }
        producedMove?.let {
            gamePosition.value = it.producePosition(pos)
            if (pos.gameState() == GameState.End) {
                gameStateRender.value = true
            }
        }
    }

    private fun pieceToMoveSelector(elementIndex: UByte) {
        if (pos.getIndexColor(elementIndex) != Piece.EMPTY) {
            selectedButton.value = elementIndex
        } else {
            selectedButton.value = null
        }
    }

    @Composable
    fun MainPage() {
        DrawBoard()
        /*Text("free pieces: ${pos.freePieces.first} (green) and ${pos.freePieces.second} (blue)")
        Text("it is now ${pos.pieceToMove.name} turn to move")
        Text(selectedButton.value.toString())*/
        Locate(Alignment.TopStart) {
            Box(
                modifier = Modifier
                    .size(BUTTON_WIDTH)
                    .background(Color.Green, CircleShape),
                Alignment.Center
            ) {
                Text(color = Color.Blue,
                    text = pos.freePieces.first.toString()
                )
            }
        }
        Locate(Alignment.TopEnd) {
            Box(
                modifier = Modifier
                    .size(BUTTON_WIDTH)
                    .background(Color.Blue, CircleShape),
                Alignment.Center
            ) {
                Text(color = Color.Green,
                    text = pos.freePieces.second.toString()
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(
                    0.dp,
                    BUTTON_WIDTH * 12,
                    0.dp,
                    0.dp
                )
                .fillMaxSize(),
            Alignment.Center
        ) {
            Button(
                onClick = {

                }
            ) {
                Text("Solve")
            }
        }
    }
}