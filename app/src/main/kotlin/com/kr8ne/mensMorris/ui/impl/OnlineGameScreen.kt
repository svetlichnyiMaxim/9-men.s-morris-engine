package com.kr8ne.mensMorris.ui.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.kr8ne.mensMorris.api.Client
import com.kr8ne.mensMorris.common.utils.AppTheme
import com.kr8ne.mensMorris.ui.interfaces.GameScreenModel
import com.kr8ne.mensMorris.viewModel.impl.GameBoardViewModel

/**
 * Game main screen
 */
class OnlineGameScreen(
    override val gameBoard: GameBoardViewModel,
    val isGreen: MutableState<Boolean?>
) : GameScreenModel {
    @Composable
    override fun InvokeRender() {
        AppTheme {
            gameBoard.render.RenderPieceCount()
            gameBoard.render.InvokeRender()
            Column {
                when (isGreen.value) {
                    true -> {
                        Text("You are green")
                    }

                    false -> {
                        Text("You are blue")
                    }

                    null -> {
                        Text("Waiting for server info")
                    }
                }
                Text(Client.jwtToken.toString())
            }
        }
    }
}
