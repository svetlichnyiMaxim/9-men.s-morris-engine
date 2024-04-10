package com.example.mensmorris.model.impl

import androidx.lifecycle.ViewModel
import com.example.mensmorris.data.impl.GameWithBotData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.GameWithBotScreen
import com.example.mensmorris.model.ViewModelInterface

/**
 * game with bot model
 */
class GameWithBotViewModel : ViewModelInterface, ViewModel() {
    override var render: ScreenModel
    override val data = GameWithBotData(this)

    init {
        val gameBoard = data.gameBoard
        render = GameWithBotScreen(gameBoard)
    }
}
