package com.kroune.mensMorris.ui.impl.game

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.kroune.mensMorris.common.AppTheme
import com.kroune.mensMorris.data.remote.Common.jwtToken
import com.kroune.mensMorris.ui.interfaces.ScreenModel
import com.kroune.mensMorris.viewModel.impl.game.OnlineGameViewModel

/**
 * Game main screen
 */
class OnlineGameScreen(
    id: Long, navContoller: NavHostController
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
