package com.kroune.nineMensMorrisApp.ui.impl.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.Position
import com.kroune.nineMensMorrisApp.BUTTON_WIDTH
import com.kroune.nineMensMorrisApp.Navigation
import com.kroune.nineMensMorrisApp.common.AppTheme
import com.kroune.nineMensMorrisApp.navigateSingleTopTo
import com.kroune.nineMensMorrisApp.ui.interfaces.ScreenModelI

/**
 * screen that is shown at the end
 */
class GameEndScreen(
    pos: Position,
    /**
     * navigation controller
     */
    private val navController: NavHostController?
) : ScreenModelI {
    private val gameBoard: GameBoardScreen =
        GameBoardScreen(pos = pos, navController = navController)

    @Composable
    override fun InvokeRender() {
        AppTheme {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    gameBoard.InvokeRender()
                    gameBoard.RenderPieceCount()
                }
                DrawButtons()
            }
        }
    }

    /**
     * draws screen after the game has ended
     */
    @Composable
    private fun DrawButtons() {
        Box(
            modifier = Modifier
                .padding(0.dp, BUTTON_WIDTH * 0.5f, 0.dp, 0.dp)
        ) {
            Text(fontSize = 30.sp, text = "Game has ended")
        }
        Box(
            modifier = Modifier
                .padding(0.dp, BUTTON_WIDTH * 6, 0.dp, 0.dp)
        ) {
            Button(onClick = {
                navController?.navigateSingleTopTo(Navigation.Welcome)
            }) {
                Text("Reset")
            }
        }
    }
}
