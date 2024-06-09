package com.kr8ne.mensMorris.ui.impl.tutorial.domain

import android.content.res.Resources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.kr8ne.mensMorris.BUTTON_WIDTH
import com.kr8ne.mensMorris.Position
import com.kr8ne.mensMorris.R
import com.kr8ne.mensMorris.common.BLUE_
import com.kr8ne.mensMorris.common.EMPTY
import com.kr8ne.mensMorris.common.GREEN
import com.kr8ne.mensMorris.ui.impl.game.GameBoardScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel

/**
 * this screen tells about information indicators provide
 */
class IndicatorsTutorialScreen(private val resources: Resources) : ScreenModel {
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

    private val gameBoard = GameBoardScreen(position, navController = null)

    @Composable
    override fun InvokeRender() {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Box {
                gameBoard.RenderPieceCount()
                gameBoard.InvokeRender()
            }
            Column(
                modifier = Modifier.padding(start = BUTTON_WIDTH, end = BUTTON_WIDTH),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = resources.getString(R.string.tutorial_indicator_piece_count),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = resources.getString(R.string.tutorial_fly_condition),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
