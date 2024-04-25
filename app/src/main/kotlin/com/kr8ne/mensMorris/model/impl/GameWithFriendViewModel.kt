package com.kr8ne.mensMorris.model.impl

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.data.impl.GameWithFriendData
import com.kr8ne.mensMorris.domain.impl.GameWithFriendScreen
import com.kr8ne.mensMorris.domain.interfaces.ScreenModel
import com.kr8ne.mensMorris.model.interfaces.ViewModelI

/**
 * game with friend model
 */
class GameWithFriendViewModel(navController: NavHostController) : ViewModelI() {
    override val data = GameWithFriendData(navController = navController)
    override var render: ScreenModel = GameWithFriendScreen(data.gameBoard)
}
