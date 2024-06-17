package com.kroune.mensMorris.ui.impl.game

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.kroune.mensMorris.common.AppTheme
import com.kroune.mensMorris.ui.interfaces.ScreenModel
import com.kroune.mensMorris.viewModel.impl.game.GameWithBotViewModel

/**
 * Game main screen
 */
class GameWithBotScreen(
    navController: NavHostController
) : ScreenModel {

    @Composable
    override fun InvokeRender() {
        AppTheme {
            viewModel.gameBoard.let {
                it.InvokeRender()
                it.RenderPieceCount()
                it.RenderUndoRedo()
            }
        }
    }

    override val viewModel = GameWithBotViewModel(navController)
}
