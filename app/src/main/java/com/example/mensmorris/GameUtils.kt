package com.example.mensmorris

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

val gameStartPosition = Position(
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

@Composable
inline fun Locate(alignment: Alignment, function: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        alignment
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