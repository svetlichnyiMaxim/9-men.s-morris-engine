package com.kr8ne.mensMorris.ui.impl.tutorial.domain

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.kr8ne.mensMorris.common.BLUE_
import com.kr8ne.mensMorris.BUTTON_WIDTH
import com.kr8ne.mensMorris.Position
import com.kr8ne.mensMorris.common.EMPTY
import com.kr8ne.mensMorris.common.GREEN
import com.kr8ne.mensMorris.R
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.getString
import com.kr8ne.mensMorris.viewModel.impl.GameBoardViewModel

/**
 * this screen tells about information indicators provide
 */
class LoseTutorialScreen : ScreenModel {
    private val position = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  BLUE_,                  BLUE_,
                    GREEN,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  BLUE_,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  BLUE_,                  EMPTY
        ),
        // @formatter:on
        freePieces = Pair(0u, 0u), pieceToMove = true, removalCount = 0
    )

    private val gameBoard = GameBoardViewModel(position, navController = null)

    @Composable
    override fun InvokeRender() {
        // TODO: add animations
        gameBoard.render.RenderPieceCount()
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(15))
                .padding(bottom = BUTTON_WIDTH * 3),
            Alignment.BottomCenter
        ) {
            Column {
                gameBoard.InvokeRender()
                Text(text = getString(R.string.tutorial_lose_condition))
            }
        }
    }
}
