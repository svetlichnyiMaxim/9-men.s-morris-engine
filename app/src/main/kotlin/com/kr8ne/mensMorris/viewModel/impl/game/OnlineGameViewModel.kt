package com.kr8ne.mensMorris.viewModel.impl.game

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.data.local.impl.game.OnlineGameData
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * welcome model
 * called when app is launched
 */
class OnlineGameViewModel(navController: NavHostController?, id: Long) :
    ViewModelI() {
    override val data = OnlineGameData(id, navController)
}
