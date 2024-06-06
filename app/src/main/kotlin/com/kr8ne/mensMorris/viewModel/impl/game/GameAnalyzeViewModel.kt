package com.kr8ne.mensMorris.viewModel.impl.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.kr8ne.mensMorris.Position
import com.kr8ne.mensMorris.common.toPositions
import com.kr8ne.mensMorris.data.impl.game.GameAnalyzeData
import com.kr8ne.mensMorris.ui.impl.game.GameAnalyzeScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * game analyze model
 */
class GameAnalyzeViewModel(
    /**
     * winning positions consequence
     */
    val pos: MutableState<Position>
) : ViewModelI() {

    override val data = GameAnalyzeData(pos)

    private val positionsToDisplay: MutableState<List<Position>> = mutableStateOf(listOf())
    override var render: ScreenModel = GameAnalyzeScreen(
        positionsToDisplay,
        data.depth,
        { data.increaseDepth() },
        { data.decreaseDepth() },
        { data.startAnalyze() },
        { InvokeTransformation() }
    )

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
