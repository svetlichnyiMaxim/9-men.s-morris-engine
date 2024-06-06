package com.kr8ne.mensMorris.data.local.impl.game

import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.cache.Cache
import com.kr8ne.mensMorris.data.local.interfaces.DataModel
import com.kr8ne.mensMorris.data.local.interfaces.GameBoardInterface
import com.kr8ne.mensMorris.gameStartPosition
import com.kr8ne.mensMorris.viewModel.impl.game.GameBoardViewModel

/**
 * data for game with friend screen
 */
class GameWithFriendData(navController: NavHostController?) :
    DataModel, GameBoardInterface {
    override val gameBoard = GameBoardViewModel(
        pos = mutableStateOf(gameStartPosition),
        navController = navController
    )

    override suspend fun invokeBackend() {
        Cache.resetCacheDepth()
    }
}
