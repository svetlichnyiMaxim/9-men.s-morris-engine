package com.kroune.mensMorris.viewModel.impl.game

import com.kroune.mensMorris.data.local.impl.game.GameWithFriendData
import com.kroune.mensMorris.viewModel.interfaces.ViewModelI

/**
 * game with friend model
 */
class GameWithFriendViewModel : ViewModelI() {
    override val data = GameWithFriendData()
}
