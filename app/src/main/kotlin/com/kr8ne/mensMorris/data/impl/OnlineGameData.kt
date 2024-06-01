package com.kr8ne.mensMorris.data.impl

import com.kr8ne.mensMorris.api.Client
import com.kr8ne.mensMorris.api.Client.positionsQueue
import com.kr8ne.mensMorris.data.interfaces.DataModel
import com.kr8ne.mensMorris.data.interfaces.GameBoardInterface
import com.kr8ne.mensMorris.viewModel.impl.GameBoardViewModel

/**
 * data for online game screen
 */
class OnlineGameData(
    private val gameId: Long
) : GameBoardInterface, DataModel {
    override val gameBoard = GameBoardViewModel(
        onClick = { index, func -> response(index, func) },
        navController = null
    )

    private fun response(index: Int, func: (index: Int) -> Unit) {
        gameBoard.data.getMovement(index)?.let {
            Client.movesQueue.add(it)
        }
        println("move added to the queue")
        func(index)
    }

    override suspend fun invokeBackend() {
        Client.playGame(gameId)
        positionsQueue.observeForever {
            while (it.isNotEmpty()) {
                gameBoard.data.pos.value.positions = it.peek()!!.positions
                gameBoard.data.pos.value.pieceToMove = it.peek()!!.pieceToMove
                gameBoard.data.pos.value.freePieces = it.peek()!!.freePieces
                gameBoard.data.pos.value.removalCount = it.peek()!!.removalCount
                println("new position")
            }
        }
    }
}
