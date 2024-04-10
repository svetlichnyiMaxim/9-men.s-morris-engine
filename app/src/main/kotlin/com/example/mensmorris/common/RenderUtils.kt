package com.example.mensmorris.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * provides a quicker way for setting element position
 * @param alignment our alignment (pos)
 * @param function all code we want to run at this function
 */
@Composable
inline fun Locate(alignment: Alignment, function: () -> Unit) {
    Box(
        modifier = Modifier
            , alignment
    ) {
        function()
    }
}


/**
 * adds a basic background
 * @param function everything ui-related that happens inside of the app
 */
@Composable
inline fun AppTheme(function: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF7E7E7E))
    ) {
        function()
    }
}
