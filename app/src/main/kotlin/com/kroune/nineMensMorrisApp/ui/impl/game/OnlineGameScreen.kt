package com.kroune.nineMensMorrisApp.ui.impl.game

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.kroune.nineMensMorrisApp.common.AppTheme
import com.kroune.nineMensMorrisApp.data.remote.Common.jwtToken
import com.kroune.nineMensMorrisApp.ui.interfaces.ScreenModelI
import com.kroune.nineMensMorrisApp.viewModel.impl.game.OnlineGameViewModel

/**
 * Game main screen
 */
class OnlineGameScreen(
    id: Long, navController: NavHostController
) : ScreenModelI {
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

    override val viewModel = OnlineGameViewModel(navController, id)
}
