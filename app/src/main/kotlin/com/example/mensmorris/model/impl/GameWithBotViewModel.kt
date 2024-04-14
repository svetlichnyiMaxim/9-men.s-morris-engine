package com.example.mensmorris.model.impl

import com.example.mensmorris.data.impl.GameWithBotData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.GameWithBotScreen
import com.example.mensmorris.model.ViewModelI

/**
 * game with bot model
 */
class GameWithBotViewModel : ViewModelI() {
    override var render: ScreenModel
    override val data = GameWithBotData(this)

    init {
        val gameBoard = data.gameBoard
        render = GameWithBotScreen(gameBoard)
    }
}
