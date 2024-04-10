package com.example.mensmorris.model.impl

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mensmorris.common.CustomMutableLiveData
import com.example.mensmorris.common.Movement
import com.example.mensmorris.common.Position
import com.example.mensmorris.common.toPositions
import com.example.mensmorris.data.impl.GameAnalyzeData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.GameAnalyzeScreen
import com.example.mensmorris.model.ViewModelInterface

/**
 * game analyze model
 */
class GameAnalyzeViewModel(
    /**
     * winning positions consequence
     */
    val pos: MutableState<Position>
) : ViewModelInterface {

    override var render: ScreenModel
    override val data = GameAnalyzeData(pos)

    private val positionsToDisplay: MutableState<List<Position>> = mutableStateOf(listOf())

    private val solveResultListener: CustomMutableLiveData<List<Movement>> =
        (data.solveResult as CustomMutableLiveData<List<Movement>>).apply {
            function = {
                positionsToDisplay.value = this.value!!.toPositions(pos.value)
            }
        }

    init {
        solveResultListener
        render = GameAnalyzeScreen(positionsToDisplay,
            data.depth,
            { data.increaseDepth() },
            { data.decreaseDepth() },
            { data.startAnalyze() })
    }
}
