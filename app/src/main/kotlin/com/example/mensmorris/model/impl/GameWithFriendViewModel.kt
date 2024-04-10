package com.example.mensmorris.model.impl

import androidx.lifecycle.ViewModel
import com.example.mensmorris.data.impl.GameWithFriendData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.GameWithFriendScreen
import com.example.mensmorris.model.impl.tutorial.ViewModelInterface

/**
 * game with friend model
 */
class GameWithFriendViewModel : ViewModelInterface, ViewModel() {
    override var render: ScreenModel
    override val data = GameWithFriendData(this)

    init {
        val gameBoard = data.gameBoard
        render = GameWithFriendScreen(gameBoard)
    }
}
