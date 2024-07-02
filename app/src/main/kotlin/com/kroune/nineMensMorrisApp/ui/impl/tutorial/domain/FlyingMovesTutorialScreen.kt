package com.kroune.nineMensMorrisApp.ui.impl.tutorial.domain

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
import com.kroune.nineMensMorrisApp.BUTTON_WIDTH
import com.kroune.nineMensMorrisApp.R
import com.kroune.nineMensMorrisApp.ui.impl.game.GameBoardScreen
import com.kroune.nineMensMorrisApp.ui.interfaces.ScreenModelI

/**
 * this screen tells about information indicators provide
 */
class FlyingMovesTutorialScreen(private val resources: Resources) : ScreenModelI {
    private val position = Position(
        // @formatter:off
        arrayOf(
            BLUE_,                  EMPTY,                  EMPTY,
                    GREEN,          EMPTY,          EMPTY,
                            EMPTY,  EMPTY,  BLUE_,
            EMPTY,  GREEN,  EMPTY,          EMPTY,  EMPTY,  GREEN,
                            EMPTY,  EMPTY,  EMPTY,
                    EMPTY,          EMPTY,          EMPTY,
            EMPTY,                  BLUE_,                  BLUE_
        ),
        // @formatter:on
        0u, 0u, pieceToMove = true, removalCount = 0u
    )

    private val gameBoard = GameBoardScreen(
        pos = position,
        navController = null
    )

    init {
        gameBoard.viewModel.onClick(3)
    }

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
                    text = resources.getString(R.string.tutorial_fly_condition),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = resources.getString(R.string.tutorial_fly_highlighting),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
