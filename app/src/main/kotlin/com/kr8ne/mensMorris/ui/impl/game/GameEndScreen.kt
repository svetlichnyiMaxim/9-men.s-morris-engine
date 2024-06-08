package com.kr8ne.mensMorris.ui.impl.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.BUTTON_WIDTH
import com.kr8ne.mensMorris.Position
import com.kr8ne.mensMorris.WELCOME_SCREEN
import com.kr8ne.mensMorris.common.AppTheme
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.impl.game.GameEndViewModel

/**
 * screen that is shown at the end
 */
class GameEndScreen(
    var gameBoard: GameBoardScreen,
    /**
     * navigation controller
     */
    val navController: NavHostController?
) : ScreenModel {
    constructor(pos: Position, navController: NavHostController) : this(
        GameBoardScreen(pos = pos, navController = navController), navController
    )

    @Composable
    override fun InvokeRender() {
        AppTheme {
            DrawButtons()
            gameBoard.InvokeRender()
        }
    }

    override val viewModel = GameEndViewModel(gameBoard, navController)

    /**
     * draws screen after the game has ended
     */
    @Composable
    private fun DrawButtons() {
        Box(
            modifier = Modifier
                .fillMaxWidth(), Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(0.dp, BUTTON_WIDTH * 0.5f, 0.dp, 0.dp)
                    .fillMaxSize(), Alignment.Center
            ) {
                Text(fontSize = 30.sp, text = "Game has ended")
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(), Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .size(
                            BUTTON_WIDTH * if (gameBoard.posState.value.pieceToMove
                            ) 1.5f else 1f
                        )
                        .background(Color.Green, CircleShape), Alignment.Center
                ) {
                    Text(color = Color.Blue, text = gameBoard.posState.value.freePieces.first.toString())
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(), Alignment.TopEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(BUTTON_WIDTH * if (!gameBoard.posState.value.pieceToMove) 1.5f else 1f)
                        .background(Color.Blue, CircleShape), Alignment.Center
                ) {
                    Text(color = Color.Green, text = gameBoard.posState.value.freePieces.second.toString())
                }
            }
            Box(
                modifier = Modifier
                    .padding(0.dp, BUTTON_WIDTH * 10, 0.dp, 0.dp)
                    .fillMaxSize(), Alignment.Center
            ) {
                Button(onClick = {
                    navController?.navigate(WELCOME_SCREEN)
                }) {
                    Text("Reset")
                }
            }
        }
    }
}
