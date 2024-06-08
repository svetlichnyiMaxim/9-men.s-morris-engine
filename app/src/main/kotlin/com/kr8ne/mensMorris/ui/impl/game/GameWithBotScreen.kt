package com.kr8ne.mensMorris.ui.impl.game

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.common.AppTheme
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.impl.game.GameWithBotViewModel

/**
 * Game main screen
 */
class GameWithBotScreen(
    val navController: NavHostController
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
