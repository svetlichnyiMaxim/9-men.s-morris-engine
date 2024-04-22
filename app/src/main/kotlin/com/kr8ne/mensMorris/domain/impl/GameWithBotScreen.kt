package com.kr8ne.mensMorris.domain.impl

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.kr8ne.mensMorris.common.gameBoard.GameBoard
import com.kr8ne.mensMorris.common.utils.AppTheme
import com.kr8ne.mensMorris.domain.interfaces.GameScreenModel

/**
 * Game main screen
 */
class GameWithBotScreen(
    override val gameBoard: GameBoard
) : ViewModel(), GameScreenModel {

    @Composable
    override fun InvokeRender() {
        AppTheme {
            gameBoard.RenderBoard()
            gameBoard.RenderPieceCount()
            gameBoard.RenderUndoRedo()
        }
    }
}
