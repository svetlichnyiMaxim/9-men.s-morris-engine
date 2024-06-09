package com.kr8ne.mensMorris.ui.impl.game

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.common.AppTheme
import com.kr8ne.mensMorris.data.remote.jwtToken
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.impl.game.OnlineGameViewModel

/**
 * Game main screen
 */
class OnlineGameScreen(
    id: Long,
    navContoller: NavHostController
) : ScreenModel {
    @Composable
    override fun InvokeRender() {
        AppTheme {
            viewModel.data.gameBoard.RenderPieceCount()
            viewModel.data.gameBoard.InvokeRender()
            Column {
                when (viewModel.data.isGreen.value) {
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
                Text(jwtToken.toString())
            }
        }
    }

    override val viewModel = OnlineGameViewModel(navContoller, id)
}
