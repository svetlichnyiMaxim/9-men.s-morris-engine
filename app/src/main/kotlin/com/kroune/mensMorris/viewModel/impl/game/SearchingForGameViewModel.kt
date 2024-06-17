package com.kroune.mensMorris.viewModel.impl.game

import androidx.navigation.NavHostController
import com.kroune.mensMorris.data.local.impl.game.SearchingForGameData
import com.kroune.mensMorris.viewModel.interfaces.ViewModelI

/**
 * game with bot model
 */
class SearchingForGameViewModel(navController: NavHostController?) : ViewModelI() {
    override val data = SearchingForGameData(navController)
}
