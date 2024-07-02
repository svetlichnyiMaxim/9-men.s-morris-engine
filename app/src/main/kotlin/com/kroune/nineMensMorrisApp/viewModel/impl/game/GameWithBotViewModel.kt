package com.kroune.nineMensMorrisApp.viewModel.impl.game

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.kroune.nineMensMorrisApp.data.local.impl.game.GameWithBotData
import com.kroune.nineMensMorrisApp.viewModel.interfaces.ViewModelI

/**
 * game with bot model
 */
class GameWithBotViewModel(navController: NavHostController?) : ViewModelI() {
    override val data = GameWithBotData(navController, viewModelScope)

    /**
     * quick access to the game board
     */
    val gameBoard
        get() = data.gameBoard
}
