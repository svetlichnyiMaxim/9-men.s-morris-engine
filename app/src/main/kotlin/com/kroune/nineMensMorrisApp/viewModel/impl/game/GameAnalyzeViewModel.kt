package com.kroune.nineMensMorrisApp.viewModel.impl.game

import androidx.lifecycle.viewModelScope
import com.kr8ne.mensMorris.Position
import com.kroune.nineMensMorrisApp.common.toPositions
import com.kroune.nineMensMorrisApp.data.local.impl.game.GameAnalyzeData
import com.kroune.nineMensMorrisApp.viewModel.interfaces.ViewModelI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * game analyze model
 */
class GameAnalyzeViewModel(
    /**
     * winning positions consequence
     */
    val pos: MutableStateFlow<Position>
) : ViewModelI() {

    override val data = GameAnalyzeData(pos)

    private val _uiState: MutableStateFlow<GameAnalyzeUiState> =
        MutableStateFlow(GameAnalyzeUiState(mutableListOf(), 3))

    @Suppress("UndocumentedPublicProperty")
    val uiState: StateFlow<GameAnalyzeUiState>
        get() = _uiState

    init {
        viewModelScope.launch {
            data.dataState
                .collect { (positions, depth) ->
                    _uiState.value =
                        GameAnalyzeUiState(positions.toPositions(data.pos.value), depth)
                }
        }
    }

    /**
     * quick access function
     */
    fun increaseDepth() {
        data.increaseDepth()
    }

    /**
     * quick access function
     */
    fun decreaseDepth() {
        data.decreaseDepth()
    }

    /**
     * quick access function
     */
    fun startAnalyze() {
        data.startAnalyze()
    }
}

/**
 * ui state of the game analyze screen
 */
class GameAnalyzeUiState(
    /**
     * currently analyzed position
     */
    val positions: List<Position>,
    /**
     * current analyze depth
     */
    val depth: Int
)
