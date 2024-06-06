package com.kr8ne.mensMorris.viewModel.impl.game

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.data.local.impl.game.OnlineGameData
import com.kr8ne.mensMorris.data.remote.Game
import com.kr8ne.mensMorris.ui.impl.game.OnlineGameScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * welcome model
 * called when app is launched
 */
class OnlineGameViewModel(navController: NavHostController?) :
    ViewModelI() {
    override val data = OnlineGameData(Game.gameId!!, navController)
    override var render: ScreenModel = OnlineGameScreen(data.gameBoard, data.isGreen)
}
