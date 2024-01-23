package com.example.mensmorris.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mensmorris.ui.AppTheme
import com.example.mensmorris.ui.BUTTON_WIDTH
import com.example.mensmorris.ui.render


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
                render {
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