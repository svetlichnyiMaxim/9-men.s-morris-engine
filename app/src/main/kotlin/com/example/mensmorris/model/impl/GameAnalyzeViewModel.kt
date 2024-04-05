package com.example.mensmorris.model.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mensmorris.common.Position
import com.example.mensmorris.common.toPositions
import com.example.mensmorris.data.impl.GameAnalyzeData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.GameAnalyzeScreen
import com.example.mensmorris.model.ViewModelInterface

class GameAnalyzeViewModel(val position: MutableState<Position>) : ViewModelInterface {
    override var render: ScreenModel
    override val data = GameAnalyzeData(position)

    val positionsToDisplay: MutableState<List<Position>> = mutableStateOf(listOf())

    // TODO: this solution is shit find normal way
    @Composable
    fun SolveResultPresenter() {
        val solveResult = data.solveResult.value
        positionsToDisplay.value = solveResult.toPositions(position.value)
    }

    init {
        render = GameAnalyzeScreen(positionsToDisplay,
            data.depth,
            { data.increaseDepth() },
            { data.decreaseDepth() },
            { data.startAnalyze() })
    }
}
