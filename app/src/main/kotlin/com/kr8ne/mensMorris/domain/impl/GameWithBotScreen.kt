package com.kr8ne.mensMorris.domain.impl

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.kr8ne.mensMorris.common.gameBoard.GameBoard
import com.kr8ne.mensMorris.common.utils.AppTheme
import com.kr8ne.mensMorris.domain.GameScreenModel

/**
 * Game main screen
 */
class GameWithBotScreen(
    override var gameBoard: GameBoard
) : ViewModel(), GameScreenModel {

    private val pieceCountFragment = PieceCountFragment(gameBoard.pos)

    @Composable
    override fun InvokeRender() {
        AppTheme {
            gameBoard.Draw()
            pieceCountFragment.InvokeRender()
            gameBoard.RenderUndoRedo()
        }
    }
}
