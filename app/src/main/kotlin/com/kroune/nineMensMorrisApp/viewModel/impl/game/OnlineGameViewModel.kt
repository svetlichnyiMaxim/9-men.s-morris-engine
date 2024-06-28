package com.kroune.nineMensMorrisApp.viewModel.impl.game

import androidx.navigation.NavHostController
import com.kroune.nineMensMorrisApp.data.local.impl.game.OnlineGameData
import com.kroune.nineMensMorrisApp.viewModel.interfaces.ViewModelI

/**
 * welcome model
 * called when app is launched
 */
class OnlineGameViewModel(navController: NavHostController?, id: Long) : ViewModelI() {
    override val data = OnlineGameData(id, navController)
}
