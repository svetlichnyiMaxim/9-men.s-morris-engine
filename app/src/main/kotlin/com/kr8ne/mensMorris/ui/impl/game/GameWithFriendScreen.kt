package com.kr8ne.mensMorris.ui.impl.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kr8ne.mensMorris.BUTTON_WIDTH
import com.kr8ne.mensMorris.common.AppTheme
import com.kr8ne.mensMorris.ui.interfaces.GameScreenModel
import com.kr8ne.mensMorris.viewModel.impl.game.GameAnalyzeViewModel
import com.kr8ne.mensMorris.viewModel.impl.game.GameBoardViewModel

/**
 * Game main screen
 */
class GameWithFriendScreen(override var gameBoard: GameBoardViewModel) : GameScreenModel {


    @Composable
    override fun InvokeRender() {
        AppTheme {
            gameBoard.render.RenderPieceCount()
            DrawMainPage()
            gameBoard.render.InvokeRender()
            gameBoard.render.RenderUndoRedo()
        }
    }

    @Composable
    private fun DrawMainPage() {
        Box(
            modifier = Modifier
                .padding(0.dp, BUTTON_WIDTH * 10.5f, 0.dp, 0.dp)
                .height(IntrinsicSize.Max)
                .fillMaxWidth()
        ) {
            GameAnalyzeViewModel(gameBoard.data.pos).Invoke()
        }
    }
}
