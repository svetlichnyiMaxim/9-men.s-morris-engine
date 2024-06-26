package com.kroune.nineMensMorrisApp.viewModel.impl.game

import com.kroune.nineMensMorrisApp.data.local.impl.game.GameWithFriendData
import com.kroune.nineMensMorrisApp.viewModel.interfaces.ViewModelI

/**
 * game with friend model
 */
class GameWithFriendViewModel : ViewModelI() {
    override val data = GameWithFriendData()
}
