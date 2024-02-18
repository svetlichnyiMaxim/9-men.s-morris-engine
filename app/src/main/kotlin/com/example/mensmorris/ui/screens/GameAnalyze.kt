package com.example.mensmorris.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.mensmorris.game.decreaseDepth
import com.example.mensmorris.game.depth
import com.example.mensmorris.game.increaseDepth
import com.example.mensmorris.game.solveResult
import com.example.mensmorris.game.startAnalyze
import com.example.mensmorris.ui.Locate

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
                decreaseDepth()
            }) {
            Text("-")
        }
    }
    Locate(Alignment.TopCenter) {
        Button(onClick = {
            startAnalyze()
        }) {
            Text("Analyze (depth - ${depth.intValue})")
        }
    }
    Locate(Alignment.TopEnd) {
        Button(modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape),
            onClick = {
                increaseDepth()
            }) {
            Text("+")
        }
    }
}
