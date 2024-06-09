package com.kr8ne.mensMorris.data.local.impl.game

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.ONLINE_GAME_SCREEN
import com.kr8ne.mensMorris.data.local.interfaces.DataI
import com.kr8ne.mensMorris.data.remote.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * data for game end screen
 */
class SearchingForGameData(
    /**
     * The NavHostController instance used for navigation.
     */
    val navController: NavHostController?,
    private val gameRepository: GameRepository = GameRepository()
) : DataI() {
    override fun invokeBackend() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val playingStatus = gameRepository.isPlaying().getOrNull()
                if (playingStatus != null) {
                    println("recovering game status")
                    navController?.navigate("$ONLINE_GAME_SCREEN/$playingStatus")
                    return@launch
                }
                gameRepository.startSearchingGame()
                val newGameId = gameRepository.awaitForGameSearchEnd()?.getOrNull()
                if (newGameId != null) {
                    navController?.navigate("$ONLINE_GAME_SCREEN/$newGameId")
                    break
                } else {
                    delay(20000L)
                }
            }
        }
    }
}
