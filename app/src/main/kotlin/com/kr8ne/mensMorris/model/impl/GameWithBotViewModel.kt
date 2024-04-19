package com.kr8ne.mensMorris.model.impl

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.data.impl.GameWithBotData
import com.kr8ne.mensMorris.domain.ScreenModel
import com.kr8ne.mensMorris.domain.impl.GameWithBotScreen
import com.kr8ne.mensMorris.model.ViewModelI

/**
 * game with bot model
 */
class GameWithBotViewModel(navController: NavHostController) : ViewModelI() {
    override var render: ScreenModel
    override val data = GameWithBotData(this, navController)

    init {
        val gameBoard = data.gameBoard
        render = GameWithBotScreen(gameBoard)
    }
}
