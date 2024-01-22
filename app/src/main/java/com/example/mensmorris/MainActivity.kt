package com.example.mensmorris

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.max


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


    private val pos
        inline get() = gamePosition.value!!
    private var solveResult = mutableStateOf<MutableList<Position>>(mutableListOf())
    private var gamePosition = mutableStateOf<Position?>(null)
    private var gameStateRender = mutableStateOf<Boolean?>(null)
    private var selectedButton = mutableStateOf<UByte?>(null)
    private var depth = mutableIntStateOf(3)
    private var solving: Job? = null

    @Composable
    fun GameEnd() {
        AppTheme {
            Text("Game has ended")
            Box(
                modifier = Modifier
                    .padding(0.dp, BUTTON_WIDTH * 12, 0.dp, 0.dp)
                    .fillMaxSize(),
                Alignment.Center
            ) {
                Button(onClick = {
                    setContent {
                        AppTheme {
                            MainPage()
                        }
                    }
                }) {
                    Text("Reset")
                }
            }
        }
    }

    @Composable
    fun RenderMainBoard() {
        gamePosition = remember { mutableStateOf(gameStartPosition) }
        gameStateRender = remember { mutableStateOf(false) }
        if (gameStateRender.value == true) {
            setContent {
                GameEnd()
            }
        }
        DrawBoard()
    }

    @Composable
    fun DrawBoard(position: Position = pos) {
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
                    RowOfCircles(0, 2, 0..2, position)
                    RowOfCircles(1, 1, 3..5, position)
                    RowOfCircles(2, 0, 6..8, position)
                    Box {
                        RowOfCircles(0, 0, 9..11, position)
                        RowOfCircles(4, 0, 12..14, position)
                    }
                    RowOfCircles(2, 0, 15..17, position)
                    RowOfCircles(1, 1, 18..20, position)
                    RowOfCircles(0, 2, 21..23, position)
                }
            }
        }
    }

    @Composable
    fun RowOfCircles(padding: Int, gap: Int, range: IntRange, position: Position = pos) {
        Row(
            modifier = Modifier.padding(start = BUTTON_WIDTH * padding),
            horizontalArrangement = Arrangement.spacedBy(
                BUTTON_WIDTH * gap, Alignment.CenterHorizontally
            ),
            Alignment.CenterVertically,
        ) {
            for (i in range) {
                CircledButton(elementIndex = i, position)
            }
        }
    }

    @Composable
    fun CircledButton(elementIndex: Int, position: Position) {
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
                    containerColor = position.getIndexColor(elementIndex.toUByte()).color
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
            reset()
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
        RenderMainBoard()
        Locate(Alignment.TopStart) {
            Box(
                modifier = Modifier
                    .size(BUTTON_WIDTH * if (pos.pieceToMove == Piece.GREEN) 1.5f else 1f)
                    .background(Color.Green, CircleShape), Alignment.Center
            ) {
                Text(color = Color.Blue, text = pos.freePieces.first.toString())
            }
        }
        Locate(Alignment.TopEnd) {
            Box(
                modifier = Modifier
                    .size(BUTTON_WIDTH * if (pos.pieceToMove == Piece.BLUE_) 1.5f else 1f)
                    .background(Color.Blue, CircleShape), Alignment.Center
            ) {
                Text(color = Color.Green, text = pos.freePieces.second.toString())
            }
        }
        if (solveResult.value.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .padding(0.dp, BUTTON_WIDTH * 12, 0.dp, 0.dp)
                    .background(Color.DarkGray, RoundedCornerShape(15))
            ) {
                Column {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .weight(1f, false)
                    ) {
                        for (i in 0..<solveResult.value.size) {
                            Row {
                                DrawBoard(solveResult.value[i])
                            }
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                // BUTTON_WIDTH * 12
                .padding(0.dp, BUTTON_WIDTH * 10.5f, 0.dp, 0.dp)
                .fillMaxSize()
        )
        {
            Locate(Alignment.TopStart) {
                Button(
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.5f), CircleShape),
                    onClick = {
                        depth.intValue = max(0, depth.intValue - 1)
                        reset()
                    }) {
                    Text("-")
                }
            }
            Locate(Alignment.TopCenter) {
                Button(onClick = {
                    solving = CoroutineScope(Dispatchers.Default).launch {
                        solveResult.value = pos.solve(depth.intValue.toUByte()).second
                    }
                }) {
                    Text("Solve (depth - ${depth.intValue})")
                }
            }
            Locate(Alignment.TopEnd) {
                Button(
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.5f), CircleShape),
                    onClick = {
                        depth.intValue++
                        reset()
                    }) {
                    Text("+")
                }
            }
        }
    }

    private fun reset() {
        solveResult.value = mutableListOf()
        occurredPositions.forEach {
            occurredPositions[it.key] = Pair(it.value.first, 0u)
        }
        if (solving != null) {
            try {
                solving!!.cancel()
            } catch (_: CancellationException) {
            }
        }
    }
}