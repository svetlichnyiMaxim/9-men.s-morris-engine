package com.kroune.nineMensMorrisApp.viewModel.impl.game

import androidx.navigation.NavHostController
import com.kroune.nineMensMorrisApp.data.local.impl.game.SearchingForGameData
import com.kroune.nineMensMorrisApp.viewModel.interfaces.ViewModelI

/**
 * game with bot model
 */
class SearchingForGameViewModel(navController: NavHostController?) : ViewModelI() {
    override val data = SearchingForGameData(navController)
}
