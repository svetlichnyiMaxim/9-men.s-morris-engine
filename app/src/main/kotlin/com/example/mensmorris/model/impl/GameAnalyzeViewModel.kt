package com.example.mensmorris.model.impl

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
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
    val pos: MutableLiveData<Position>
) : ViewModelInterface {
    constructor(pos: MutableState<Position>) : this(MutableLiveData(pos.value))

    override var render: ScreenModel
    override val data = GameAnalyzeData(pos)

    private val positionsToDisplay: MutableState<List<Position>> = mutableStateOf(listOf())

    private fun solveResultObserver() {
        pos.observeForever {
            // we reset our analyze thing when position changes
            data.solveResult.value = listOf()
        }
        data.solveResult.observeForever {
            positionsToDisplay.value = it.toPositions(pos.value!!)
        }
    }

    init {
        solveResultObserver()
        render = GameAnalyzeScreen(positionsToDisplay,
            data.depth,
            { data.increaseDepth() },
            { data.decreaseDepth() },
            { data.startAnalyze() })
    }
}
