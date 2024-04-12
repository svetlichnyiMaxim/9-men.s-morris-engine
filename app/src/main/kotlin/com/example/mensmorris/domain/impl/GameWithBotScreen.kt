package com.example.mensmorris.domain.impl

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.mensmorris.common.utils.AppTheme
import com.example.mensmorris.common.gameBoard.GameBoard
import com.example.mensmorris.domain.GameScreenModel

/**
 * Game main screen
 */
class GameWithBotScreen(override var gameBoard: GameBoard) : ViewModel(), GameScreenModel {

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
