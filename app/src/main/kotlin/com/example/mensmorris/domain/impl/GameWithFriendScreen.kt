package com.example.mensmorris.domain.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.mensmorris.common.AppTheme
import com.example.mensmorris.BUTTON_WIDTH
import com.example.mensmorris.common.gameBoard.GameBoard
import com.example.mensmorris.common.Locate
import com.example.mensmorris.common.render
import com.example.mensmorris.domain.GameScreenModel
import com.example.mensmorris.common.utils.GameUtils.pos
import com.example.mensmorris.model.impl.GameAnalyzeViewModel

/**
 * Game main screen
 */
class GameWithFriendScreen(override var gameBoard: GameBoard) : GameScreenModel {

    @Composable
    override fun InvokeRender() {
        render {
            AppTheme {
                DrawMainPage()
                gameBoard.Draw()
                gameBoard.RenderUndoRedo()
            }
        }
    }

    @Composable
    private fun DrawMainPage() {
        Locate(Alignment.TopStart) {
            Box(
                modifier = Modifier
                    .size(BUTTON_WIDTH * if (pos.pieceToMove) 1.5f else 1f)
                    .background(Color.Green, CircleShape)
                    .alpha(if (pos.freePieces.first == 0.toUByte()) 0f else 1f), Alignment.Center
            ) {
                Text(color = Color.Blue, text = pos.freePieces.first.toString())
            }
        }
        Locate(Alignment.TopEnd) {
            Box(
                modifier = Modifier
                    .size(BUTTON_WIDTH * if (!pos.pieceToMove) 1.5f else 1f)
                    .background(Color.Blue, CircleShape)
                    .alpha(if (pos.freePieces.second == 0.toUByte()) 0f else 1f), Alignment.Center
            ) {
                Text(color = Color.Green, text = pos.freePieces.second.toString())
            }
        }
        Box(
            modifier = Modifier
                .padding(0.dp, BUTTON_WIDTH * 10.5f, 0.dp, 0.dp)
                .height(LocalConfiguration.current.screenHeightDp.dp - BUTTON_WIDTH * 10.5f)
                .fillMaxWidth()
        ) {
            GameAnalyzeViewModel(gameBoard.position).let {
                it.SolveResultPresenter()
                it.invoke(value = gameBoard.position)
            }
        }
    }
}
