package com.kr8ne.mensMorris.viewModel.impl

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.api.Client
import com.kr8ne.mensMorris.data.impl.OnlineGameData
import com.kr8ne.mensMorris.ui.impl.OnlineGameScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * welcome model
 * called when app is launched
 */
class OnlineGameViewModel(@Suppress("UnusedPrivateProperty") navController: NavHostController?) :
    ViewModelI() {
    override val data = OnlineGameData(Client.gameId!!)
    override var render: ScreenModel = OnlineGameScreen(data.gameBoard)
}
