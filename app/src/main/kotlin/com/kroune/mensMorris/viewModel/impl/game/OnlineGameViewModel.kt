package com.kroune.mensMorris.viewModel.impl.game

import androidx.navigation.NavHostController
import com.kroune.mensMorris.data.local.impl.game.OnlineGameData
import com.kroune.mensMorris.viewModel.interfaces.ViewModelI

/**
 * welcome model
 * called when app is launched
 */
class OnlineGameViewModel(navController: NavHostController?, id: Long) :
    ViewModelI() {
    override val data = OnlineGameData(id, navController)
}
