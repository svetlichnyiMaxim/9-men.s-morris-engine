package com.example.mensmorris.domain.impl

import androidx.compose.runtime.Composable
import com.example.mensmorris.common.AppTheme
import com.example.mensmorris.common.gameBoard.GameBoard
import com.example.mensmorris.domain.GameScreenModel

/**
 * Game main screen
 */
class GameWithBotScreen(override var gameBoard: GameBoard) : GameScreenModel {

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
