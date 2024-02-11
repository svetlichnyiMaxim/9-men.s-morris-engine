package com.example.mensmorris.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mensmorris.R
import com.example.mensmorris.game.Piece
import com.example.mensmorris.game.Position
import com.example.mensmorris.game.depth
import com.example.mensmorris.game.gameStartPosition
import com.example.mensmorris.game.hasCache
import com.example.mensmorris.game.moveHints
import com.example.mensmorris.game.pos
import com.example.mensmorris.game.resetAnalyze
import com.example.mensmorris.game.selectedButton
import com.example.mensmorris.game.solveResult
import com.example.mensmorris.game.solving
import com.example.mensmorris.ui.BUTTON_WIDTH
import com.example.mensmorris.ui.DrawBoard
import com.example.mensmorris.ui.Locate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Stack
import kotlin.math.max


/**
 * draws screen during the game
 */
@Composable
fun MainPage() {
    DrawBoard(pos)
    Locate(Alignment.TopStart) {
        Box(
            modifier = Modifier
                .size(BUTTON_WIDTH * if (pos.pieceToMove) 1.5f else 1f)
                .background(Color.Green, CircleShape)
                .alpha(if (pos.freePieces.first == 0.toUByte()) 0f else 1f), Alignment.Center
        ) {
            Text(color = Color.Blue, text = pos.freePieces.first.toString())
        }
    }
    Locate(Alignment.TopEnd) {
        Box(
            modifier = Modifier
                .size(BUTTON_WIDTH * if (!pos.pieceToMove) 1.5f else 1f)
                .background(Color.Blue, CircleShape)
                .alpha(if (pos.freePieces.second == 0.toUByte()) 0f else 1f), Alignment.Center
        ) {
            Text(color = Color.Green, text = pos.freePieces.second.toString())
        }
    }
    Box(
        modifier = Modifier
            .padding(0.dp, BUTTON_WIDTH * 10.5f, 0.dp, 0.dp)
            .fillMaxSize()
    ) {
        DrawGameAnalyze()
    }
    RenderUndo()
}

/**
 * stores all movements (positions) history
 */
val movesHistory: Stack<Position> = Stack()
/**
 * stores a moves we have undone
 * resets them if we do any other move
 */
val undoneMoveHistory: Stack<Position> = Stack()

/**
 * renders undo buttons
 */
@Composable
fun RenderUndo() {
    Locate(Alignment.BottomStart) {
        Button(modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape),
            onClick = {
                if (!movesHistory.empty()) {
                    undoneMoveHistory.push(movesHistory.peek())
                    movesHistory.pop()
                    pos = movesHistory.lastOrNull() ?: gameStartPosition
                    moveHints.value.clear()
                    selectedButton.value = null
                }
            }) {
            Icon(painter = painterResource(id = R.drawable.forward),
                "undo")
        }
    }
    Locate(Alignment.BottomEnd) {
        Button(modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape),
            onClick = {
                if (!undoneMoveHistory.empty()) {
                    movesHistory.push(undoneMoveHistory.peek())
                    undoneMoveHistory.pop()
                    pos = movesHistory.lastOrNull() ?: gameStartPosition
                    moveHints.value.clear()
                    selectedButton.value = null
                }
            }) {
            Icon(painter = painterResource(id = R.drawable.back),
                "redo")
        }
    }
}

/**
 * saves a move we have made
 */
fun saveMove(pos: Position) {
    if (undoneMoveHistory.isNotEmpty()) {
        undoneMoveHistory.clear()
    }
    movesHistory.push(pos)
}

/**
 * draws ui elements for accessing game analyzes
 */
@Composable
fun DrawGameAnalyze() {
    if (solveResult.value.isNotEmpty()) {
        DrawBestLine()
    }
    Locate(Alignment.TopStart) {
        Button(modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape),
            onClick = {
                depth.intValue = max(0, depth.intValue - 1)
                resetAnalyze()
            }) {
            Text("-")
        }
    }
    Locate(Alignment.TopCenter) {
        Button(onClick = {
            solving = CoroutineScope(Dispatchers.Default).launch {
                hasCache = true
                solveResult.value = pos.solve(depth.intValue.toUByte()).second
            }
        }) {
            Text("Analyze (depth - ${depth.intValue})")
        }
    }
    Locate(Alignment.TopEnd) {
        Button(modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape),
            onClick = {
                depth.intValue++
                resetAnalyze()
            }) {
            Text("+")
        }
    }
}

/**
 * draws all best moves (main line)
 */
@Composable
fun DrawBestLine() {
    Box(
        modifier = Modifier
            .padding(0.dp, BUTTON_WIDTH * 1.5f, 0.dp, 0.dp)
            .background(Color.DarkGray, RoundedCornerShape(5))
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
