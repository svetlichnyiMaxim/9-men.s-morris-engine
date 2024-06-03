package com.kr8ne.mensMorris.ui.impl

import androidx.compose.runtime.Composable
import com.kr8ne.mensMorris.common.utils.AppTheme
import com.kr8ne.mensMorris.ui.interfaces.GameScreenModel
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
