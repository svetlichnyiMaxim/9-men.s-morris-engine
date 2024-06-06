package com.kr8ne.mensMorris.data.local.impl.game

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.ONLINE_GAME_SCREEN
import com.kr8ne.mensMorris.data.local.interfaces.DataModel
import com.kr8ne.mensMorris.data.remote.Game
import kotlinx.coroutines.delay

/**
 * data for game end screen
 */
class SearchingForGameData(
    /**
     * The NavHostController instance used for navigation.
     */
    val navController: NavHostController?
) : DataModel {
    override suspend fun invokeBackend() {
        while (true) {
            val playingStatus = Game.isPlaying().getOrNull()
            if (playingStatus != null) {
                println("recovering game status")
                navController?.navigate("$ONLINE_GAME_SCREEN/$playingStatus")
                return
            }
            Game.startSearchingGame()
            val newGameId = Game.awaitForGameSearchEnd()?.getOrNull()
            if (newGameId != null) {
                navController?.navigate("$ONLINE_GAME_SCREEN/$newGameId")
                break
            } else {
                delay(20000L)
            }
        }
    }
}
