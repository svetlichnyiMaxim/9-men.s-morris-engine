package com.kroune.nineMensMorrisApp.ui.impl.game

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.kroune.nineMensMorrisApp.common.AppTheme
import com.kroune.nineMensMorrisApp.ui.interfaces.ScreenModelI
import com.kroune.nineMensMorrisApp.viewModel.impl.game.GameWithBotViewModel

/**
 * Game main screen
 */
class GameWithBotScreen(
    navController: NavHostController
) : ScreenModelI {

    @Composable
    override fun InvokeRender() {
        AppTheme {
            viewModel.gameBoard.let {
                it.RenderPieceCount()
                it.InvokeRender()
                it.RenderUndoRedo()
            }
        }
    }

    override val viewModel = GameWithBotViewModel(navController)
}
