package com.kroune.mensMorris.ui.impl.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kroune.mensMorris.BUTTON_WIDTH
import com.kroune.mensMorris.common.AppTheme
import com.kroune.mensMorris.ui.interfaces.GameScreenModel
import com.kroune.mensMorris.viewModel.impl.game.GameWithFriendViewModel

/**
 * Game main screen
 */
class GameWithFriendScreen(
    /**
     * navigation controller
     */
    val navController: NavHostController?,
    override var gameBoard: GameBoardScreen = GameBoardScreen(navController = navController),
) : GameScreenModel {


    @Composable
    override fun InvokeRender() {
        AppTheme {
            gameBoard.RenderPieceCount()
            DrawMainPage()
            gameBoard.InvokeRender()
            gameBoard.RenderUndoRedo()
        }
    }

    override val viewModel = GameWithFriendViewModel()

    @Composable
    private fun DrawMainPage() {
        Box(
            modifier = Modifier
                .padding(0.dp, BUTTON_WIDTH * 9.5f, 0.dp, 0.dp)
                .height(IntrinsicSize.Max)
                .fillMaxWidth()
        ) {
            GameAnalyzeScreen(gameBoard.pos).InvokeRender()
        }
    }
}
