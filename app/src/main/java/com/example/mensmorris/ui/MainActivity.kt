package com.example.mensmorris.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.unit.dp
import com.example.mensmorris.ui.screens.MainPage


val BUTTON_WIDTH = 35.dp

lateinit var mainActivity: MainActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = this
        setContent {
            AppTheme {
                MainPage()
            }
        }
    }
}