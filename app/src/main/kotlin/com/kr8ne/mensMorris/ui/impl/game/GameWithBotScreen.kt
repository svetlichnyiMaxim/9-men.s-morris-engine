package com.kr8ne.mensMorris.ui.impl.game

import androidx.compose.runtime.Composable
import com.kr8ne.mensMorris.common.AppTheme
import com.kr8ne.mensMorris.ui.interfaces.GameScreenModel
import com.kr8ne.mensMorris.viewModel.impl.game.GameBoardViewModel

/**
 * Game main screen
 */
class GameWithBotScreen(
    override val gameBoard: GameBoardViewModel
) : GameScreenModel {

    @Composable
    override fun InvokeRender() {
        AppTheme {
            gameBoard.render.InvokeRender()
            gameBoard.render.RenderPieceCount()
            gameBoard.render.RenderUndoRedo()
        }
    }
}
