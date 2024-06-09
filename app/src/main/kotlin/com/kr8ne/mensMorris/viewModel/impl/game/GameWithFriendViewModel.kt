package com.kr8ne.mensMorris.viewModel.impl.game

import com.kr8ne.mensMorris.data.local.impl.game.GameWithFriendData
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * game with friend model
 */
class GameWithFriendViewModel : ViewModelI() {
    override val data = GameWithFriendData()
}
