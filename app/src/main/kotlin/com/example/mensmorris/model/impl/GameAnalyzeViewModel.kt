package com.example.mensmorris.model.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mensmorris.common.gameBoard.Position
import com.example.mensmorris.common.utils.toPositions
import com.example.mensmorris.data.impl.GameAnalyzeData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.GameAnalyzeScreen
import com.example.mensmorris.model.ViewModelI

/**
 * game analyze model
 */
class GameAnalyzeViewModel(
    /**
     * winning positions consequence
     */
    val pos: MutableState<Position>
) : ViewModelI() {

    override var render: ScreenModel
    override val data = GameAnalyzeData(pos, this)

    private val positionsToDisplay: MutableState<List<Position>> = mutableStateOf(listOf())

    init {
        render = GameAnalyzeScreen(
            positionsToDisplay,
            data.depth,
            { data.increaseDepth() },
            { data.decreaseDepth() },
            { data.startAnalyze() },
            { InvokeTransformation() }
        )
    }

    /**
     * invokes transformation from data.solveResult to positionsToDisplay
     */
    @Composable
    fun InvokeTransformation() {
        LaunchedEffect(key1 = data.solveResult.value) {
            positionsToDisplay.value = data.solveResult.value.toPositions(pos.value)
        }
    }
}
