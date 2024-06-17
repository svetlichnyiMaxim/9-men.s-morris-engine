package com.kroune.mensMorris.ui.impl.tutorial.domain

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
import com.kr8ne.mensMorris.BLUE_
import com.kr8ne.mensMorris.EMPTY
import com.kr8ne.mensMorris.GREEN
import com.kr8ne.mensMorris.Position
import com.kroune.mensMorris.BUTTON_WIDTH
import com.kroune.mensMorris.R
import com.kroune.mensMorris.ui.impl.game.GameBoardScreen
import com.kroune.mensMorris.ui.interfaces.ScreenModel

/**
 * this screen tells about information indicators provide
 */
class PlacementTutorialScreen(private val resources: Resources) : ScreenModel {
    private val position = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  BLUE_,                  GREEN,
                    GREEN,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  EMPTY,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  BLUE_,
                            EMPTY,  GREEN,  EMPTY,
                    EMPTY,          BLUE_,          EMPTY,
            EMPTY,                  BLUE_,                  GREEN
        ),
        // @formatter:on
        1u, 2u, pieceToMove = false, removalCount = 0
    )

    private val gameBoard = GameBoardScreen(position, navController = null)

    @Composable
    override fun InvokeRender() {
        gameBoard.viewModel.handleHighLighting()
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Box {
                gameBoard.InvokeRender()
                gameBoard.RenderPieceCount()
            }
            Column(
                modifier = Modifier.padding(start = BUTTON_WIDTH, end = BUTTON_WIDTH),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = resources.getString(R.string.tutorial_placement_condition),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = resources.getString(R.string.tutorial_placement_highlighting),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
