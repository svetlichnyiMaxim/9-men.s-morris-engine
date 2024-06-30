package com.kroune.nineMensMorrisApp.viewModel.impl.game

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.kroune.nineMensMorrisApp.data.local.impl.game.OnlineGameData
import com.kroune.nineMensMorrisApp.ui.impl.game.GameBoardScreen
import com.kroune.nineMensMorrisApp.viewModel.interfaces.ViewModelI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * welcome model
 * called when app is launched
 */
class OnlineGameViewModel(navController: NavHostController?, id: Long) : ViewModelI() {
    override val data = OnlineGameData(id, navController)

    private val _uiState =
        MutableStateFlow(OnlineGameScreenUiState(data.onlineGameDataState.value.first, null))

    /**
     * exposed ui state
     */
    val uiState: StateFlow<OnlineGameScreenUiState>
        get() = _uiState

    init {
        viewModelScope.launch {
            data.onlineGameDataState
                .collect { (gameBoard, isGreen) ->
                    _uiState.value = OnlineGameScreenUiState(gameBoard, isGreen)
                }
        }
    }
}

/**
 * ui state
 */
class OnlineGameScreenUiState(
    /**
     * game board
     */
    val gameBoard: GameBoardScreen,
    /**
     * is green status
     */
    val isGreen: Boolean?
)
