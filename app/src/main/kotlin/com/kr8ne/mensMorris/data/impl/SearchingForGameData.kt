package com.kr8ne.mensMorris.data.impl

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.ONLINE_GAME_SCREEN
import com.kr8ne.mensMorris.api.Client
import com.kr8ne.mensMorris.data.interfaces.DataModel
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
            val gameId = Client.startSearchingGame().getOrNull()
            if (gameId != null) {
                println("finished searching for game with id: $gameId")
                navController?.navigate(ONLINE_GAME_SCREEN)
                break
            } else {
                delay(5000L)
            }
        }
    }
}
