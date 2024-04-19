package com.kr8ne.mensMorris.model.impl

import com.kr8ne.mensMorris.data.impl.GameWithFriendData
import com.kr8ne.mensMorris.domain.ScreenModel
import com.kr8ne.mensMorris.domain.impl.GameWithFriendScreen
import com.kr8ne.mensMorris.model.ViewModelI

/**
 * game with friend model
 */
class GameWithFriendViewModel : ViewModelI() {
    override var render: ScreenModel
    override val data = GameWithFriendData(this)

    init {
        val gameBoard = data.gameBoard
        render = GameWithFriendScreen(gameBoard)
    }
}
