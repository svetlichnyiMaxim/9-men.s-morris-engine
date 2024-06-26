package com.kroune.nineMensMorrisApp.data.remote.game

import com.kr8ne.mensMorris.move.Movement
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * interface for repository for interacting with server games
 */
interface GameRepositoryI {
    /**
     * Starts searching for a game.
     *
     * @return [ServerResponse] indicating the success or failure of the search attempt.
     */
    fun startSearchingGame()

    // TODO: remove this
    /**
     * waits for game searching result
     */
    suspend fun awaitForGameSearchEnd(): Result<Long>?

    /**
     * queue of the moves that player performed
     * TODO: implement pre-moves with this one
     */
    val movesQueue: ConcurrentLinkedQueue<Movement>

    /**
     * checks if we are currently playing a game
     */
    suspend fun isPlaying(): Result<Long?>
}
