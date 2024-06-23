package com.kroune.mensMorris.data.local.impl.game

import androidx.navigation.NavHostController
import com.kroune.mensMorris.ONLINE_GAME_SCREEN
import com.kroune.mensMorris.data.local.interfaces.DataI
import com.kroune.mensMorris.data.remote.game.GameRepository
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
                println("1")
                val playingStatus = gameRepository.isPlaying().getOrNull()
                println("2")
                if (playingStatus != null) {
                    println("recovering game status")
                    navController?.navigate("$ONLINE_GAME_SCREEN/$playingStatus")
                    return@launch
                }
                println("3")
                gameRepository.startSearchingGame()
                println("4")
                val newGameId = gameRepository.awaitForGameSearchEnd()?.getOrNull()
                println("5")
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
