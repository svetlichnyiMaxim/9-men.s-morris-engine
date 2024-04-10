package com.example.mensmorris.domain.impl.tutorial

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mensmorris.BLUE_
import com.example.mensmorris.EMPTY
import com.example.mensmorris.GREEN
import com.example.mensmorris.common.Position
import com.example.mensmorris.common.gameBoard.GameBoard
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.PieceCountFragment

class IndicatorsTutorialScreen : ScreenModel {
    private val position = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  BLUE_,                  EMPTY,
                    GREEN,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  GREEN,  EMPTY,
                    EMPTY,          BLUE_,          EMPTY,
            EMPTY,                  BLUE_,                  GREEN
        ),
        // @formatter:on
        freePieces = Pair(1u, 2u), pieceToMove = false, removalCount = 0
    )

    private val gameBoard = GameBoard(position)

    private val pieceCountFragment = PieceCountFragment(gameBoard.pos)

    @Composable
    override fun InvokeRender() {
        // TOOD: add animations
        Column {
            Row {
                gameBoard.Draw()
                pieceCountFragment.InvokeRender()
            }
            Row {
                Text(text = "Circle at the top shows how many piece you have left")
                Text(text = "Circle size shows whose turn it is")
            }
        }
    }
}

@Preview
@Composable
fun preview() {
    IndicatorsTutorialScreen().InvokeRender()
}
