package com.kr8ne.mensMorris.data.impl

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.api.Client
import com.kr8ne.mensMorris.common.game.utils.gameStartPosition
import com.kr8ne.mensMorris.data.interfaces.DataModel
import com.kr8ne.mensMorris.data.interfaces.GameBoardInterface
import com.kr8ne.mensMorris.viewModel.impl.GameBoardViewModel
import kotlinx.coroutines.launch

/**
 * data for online game screen
 */
class OnlineGameData(
    private val gameId: String
) : GameBoardInterface, DataModel {
    override val gameBoard = GameBoardViewModel(
        pos = gameStartPosition,
        onClick = { index, func -> response(index, func) },
        onUndo = { },
        navController = null
    )

    private fun response(index: Int, func: (index: Int) -> Unit) {
        if (gameBoard.data.pos.value.pieceToMove) {
            func(index)
            gameBoard.data.getMovement(index)?.let {
                Client.movesQueue.add(it)
            }
        }
    }

    override suspend fun invokeBackend() {
        Client.playGame(gameId, this)
    }
}
