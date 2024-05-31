package com.kr8ne.mensMorris.data.impl

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.ONLINE_GAME_SCREEN
import com.kr8ne.mensMorris.api.Client
import com.kr8ne.mensMorris.api.Client.awaitForGameSearchEnd
import com.kr8ne.mensMorris.common.utils.randomUtils
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
    // debug only, nuke this
    val tracker = randomUtils.nextInt()
    override suspend fun invokeBackend() {
        while (true) {
            val playingStatus = Client.isPlaying()
            if (playingStatus != null) {
                println("recovering game status")
                Client.gameId = playingStatus
                navController?.navigate(ONLINE_GAME_SCREEN)
                return
            }
            Client.startSearchingGame()
            val newGameId = awaitForGameSearchEnd()?.getOrNull()
            println("$tracker start")
            if (newGameId != null) {
                println("$tracker finished searching for game with id: $newGameId")
                Client.gameId = newGameId
                navController?.navigate(ONLINE_GAME_SCREEN)
                break
            } else {
                delay(20000L)
            }
        }
    }
}
