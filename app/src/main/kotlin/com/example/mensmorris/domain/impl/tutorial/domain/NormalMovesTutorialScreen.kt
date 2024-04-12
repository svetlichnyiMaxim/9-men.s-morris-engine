package com.example.mensmorris.domain.impl.tutorial.domain

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.mensmorris.BLUE_
import com.example.mensmorris.BUTTON_WIDTH
import com.example.mensmorris.EMPTY
import com.example.mensmorris.GREEN
import com.example.mensmorris.common.gameBoard.Position
import com.example.mensmorris.common.gameBoard.GameBoard
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.PieceCountFragment

/**
 * this screen tells about information indicators provide
 */
class NormalMovesTutorialScreen : ScreenModel {
    private val position = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  EMPTY,                  EMPTY,
                    GREEN,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  BLUE_,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
                    GREEN,          EMPTY,          GREEN,
            EMPTY,                  BLUE_,                  BLUE_
        ),
        // @formatter:on
        freePieces = Pair(0u, 0u), pieceToMove = false, removalCount = 0
    )

    private val gameBoard = GameBoard(
        position, selectedButton = mutableStateOf(22)
    )

    private val pieceCountFragment = PieceCountFragment(gameBoard.pos)

    @Composable
    override fun InvokeRender() {
        // TODO: add animations
        pieceCountFragment.InvokeRender()
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(15))
                .padding(bottom = BUTTON_WIDTH * 3),
            Alignment.BottomCenter
        ) {
            gameBoard.handleHighLighting()
            gameBoard.Draw()
            Column {
                Text(
                    text = "If you don't have any figures to place and your pieces count > 3 you " +
                            "can move to the next cell (if it is empty)"
                )
                Text(text = "Possible moves are highlighted when you have selected a piece to move")
            }
        }
    }
}
