package com.kr8ne.mensMorris.domain.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.kr8ne.mensMorris.BUTTON_WIDTH
import com.kr8ne.mensMorris.common.utils.AppTheme
import com.kr8ne.mensMorris.common.gameBoard.GameBoard
import com.kr8ne.mensMorris.domain.GameScreenModel
import com.kr8ne.mensMorris.model.impl.GameAnalyzeViewModel

/**
 * Game main screen
 */
class GameWithFriendScreen(override var gameBoard: GameBoard) : ViewModel(), GameScreenModel {

    private val pieceCountFragment = PieceCountFragment(gameBoard.pos)

    @Composable
    override fun InvokeRender() {
        AppTheme {
            pieceCountFragment.InvokeRender()
            DrawMainPage()
            gameBoard.Draw()
            gameBoard.RenderUndoRedo()
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
            GameAnalyzeViewModel(gameBoard.pos).Invoke()
        }
    }
}
