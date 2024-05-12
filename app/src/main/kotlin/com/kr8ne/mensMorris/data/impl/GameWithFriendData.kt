package com.kr8ne.mensMorris.data.impl

import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.common.game.utils.CacheUtils
import com.kr8ne.mensMorris.common.game.utils.gameStartPosition
import com.kr8ne.mensMorris.data.interfaces.DataModel
import com.kr8ne.mensMorris.data.interfaces.GameBoardInterface
import com.kr8ne.mensMorris.viewModel.impl.GameBoardViewModel

/**
 * data for game with friend screen
 */
class GameWithFriendData(navController: NavHostController) :
    DataModel, GameBoardInterface {
    override val gameBoard = GameBoardViewModel(
        pos = mutableStateOf(gameStartPosition),
        onClick = { index, func -> func(index) },
        onUndo = {},
        navController = navController
    )

    override suspend fun invokeBackend() {
        CacheUtils.resetCachedPositions()
    }
}
