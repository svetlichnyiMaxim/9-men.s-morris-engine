package com.kr8ne.mensMorris.viewModel.impl.game

import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.kr8ne.mensMorris.Position
import com.kr8ne.mensMorris.common.toPositions
import com.kr8ne.mensMorris.data.local.impl.game.GameAnalyzeData
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI
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
    val uiState: StateFlow<GameAnalyzeUiState>
        get() = _uiState

    init {
        viewModelScope.launch {
            data.result
                .collect { (positions, depth) ->
                    positions.toPositions(data.pos.value).forEach {
                        println(it)
                    }
                    _uiState.value =
                        GameAnalyzeUiState(positions.toPositions(data.pos.value), depth)
                }
        }
    }

    fun increaseDepth() {
        data.increaseDepth()
    }

    fun decreaseDepth() {
        data.decreaseDepth()
    }

    fun startAnalyze() {
        data.startAnalyze()
    }
}

class GameAnalyzeUiState(val positions: List<Position>, val depth: Int)

