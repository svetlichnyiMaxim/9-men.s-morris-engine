package com.kroune.mensMorris.viewModel.impl.game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.Position
import com.kr8ne.mensMorris.gameStartPosition
import com.kroune.mensMorris.data.local.impl.game.GameBoardData
import com.kroune.mensMorris.viewModel.interfaces.ViewModelI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * view model for game board
 */
@Suppress("LongParameterList")
class GameBoardViewModel(
    /**
     * stores current position
     */
    pos: Position = gameStartPosition,
    /**
     * what will happen if we click some circle
     */
    onClick: GameBoardData.(index: Int) -> Unit,
    /**
     * what we should additionally do on undo
     */
    onUndo: () -> Unit = {},
    /**
     * what we should execute on redo
     */
    onRedo: () -> Unit = {},
    /**
     * stores all pieces which can be moved (used for highlighting)
     */
    moveHints: List<Int> = listOf(),
    /**
     * used for storing info of the previous (valid one) clicked button
     */
    selectedButton: MutableState<Int?> = mutableStateOf(null),
    /**
     * navigation controller
     */
    navController: NavHostController?
) : ViewModelI() {

    override val data =
        GameBoardData(
            MutableStateFlow(pos),
            MutableStateFlow(moveHints),
            onUndo,
            onRedo,
            onClick,
            selectedButton,
            navController
        )

    private val _uiState: MutableStateFlow<GameBoardUiState> =
        MutableStateFlow(GameBoardUiState(pos, moveHints))

    /**
     * current app ui state
     */
    val uiState: StateFlow<GameBoardUiState> = _uiState

    init {
        viewModelScope.launch {
            data.pos
                .collect { pos ->
                    _uiState.value = _uiState.value.copy(pos = pos)
                }
        }
        viewModelScope.launch {
            data.moveHints
                .collect { moveHints ->
                    _uiState.value = _uiState.value.copy(moveHints = moveHints)
                }
        }
    }

    /**
     * quick access
     */
    fun onClick(index: Int) {
        return data.onClick(data, index)
    }

    /**
     * quick access
     */
    fun handleUndo() {
        return data.handleUndo()
    }

    /**
     * quick access
     */
    fun handleRedo() {
        return data.handleRedo()
    }

    /**
     * quick access
     */
    fun handleHighLighting() {
        data.handleHighLighting()
    }
}

/**
 * represents current ui state
 */
data class GameBoardUiState(
    /**
     * current position
     */
    val pos: Position,
    /**
     * all possible moves
     */
    val moveHints: List<Int>
)

