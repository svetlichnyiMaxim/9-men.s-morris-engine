package com.kr8ne.mensMorris.domain.impl

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.kr8ne.mensMorris.common.utils.AppTheme
import com.kr8ne.mensMorris.domain.interfaces.GameScreenModel
import com.kr8ne.mensMorris.model.impl.GameBoardViewModel

/**
 * Game main screen
 */
class GameWithBotScreen(
    override val gameBoard: GameBoardViewModel
) : ViewModel(), GameScreenModel {

    @Composable
    override fun InvokeRender() {
        AppTheme {
            gameBoard.render.InvokeRender()
            gameBoard.render.RenderPieceCount()
            gameBoard.render.RenderUndoRedo()
        }
    }
}
