package com.kr8ne.mensMorris.ui.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kr8ne.mensMorris.BUTTON_WIDTH
import com.kr8ne.mensMorris.common.game.utils.gameStartPosition
import com.kr8ne.mensMorris.common.utils.AppTheme
import com.kr8ne.mensMorris.ui.interfaces.GameScreenModel
import com.kr8ne.mensMorris.viewModel.impl.GameAnalyzeViewModel
import com.kr8ne.mensMorris.viewModel.impl.GameBoardViewModel

/**
 * Game main screen
 */
class OnlineGameScreen(
    override val gameBoard: GameBoardViewModel
) : GameScreenModel {
    @Composable
    override fun InvokeRender() {
        AppTheme {
            gameBoard.render.RenderPieceCount()
            gameBoard.render.InvokeRender()
        }
    }
}
