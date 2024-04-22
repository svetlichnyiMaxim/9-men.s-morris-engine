package com.kr8ne.mensMorris.domain.impl.tutorial.domain

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.kr8ne.mensMorris.BLUE_
import com.kr8ne.mensMorris.BUTTON_WIDTH
import com.kr8ne.mensMorris.EMPTY
import com.kr8ne.mensMorris.GREEN
import com.kr8ne.mensMorris.common.gameBoard.GameBoard
import com.kr8ne.mensMorris.common.gameBoard.Position
import com.kr8ne.mensMorris.domain.interfaces.ScreenModel

/**
 * this screen tells about information indicators provide
 */
class RemovalMovesTutorialScreen : ScreenModel {
    private val position = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  BLUE_,                  BLUE_,
                    GREEN,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  GREEN,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  BLUE_,                  GREEN
        ),
        // @formatter:on
        freePieces = Pair(0u, 0u), pieceToMove = false, removalCount = 1
    )

    private val gameBoard = GameBoard(position, navController = null)

    @Composable
    override fun InvokeRender() {
        // TODO: add animations
        gameBoard.RenderPieceCount()
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(15))
                .padding(bottom = BUTTON_WIDTH * 3),
            Alignment.BottomCenter
        ) {
            Column {
                gameBoard.handleHighLighting()
                gameBoard.RenderBoard()
                Text(text = "If you can remove enemies piece simply click on it")
            }
        }
    }
}
