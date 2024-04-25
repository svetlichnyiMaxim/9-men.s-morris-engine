package com.kr8ne.mensMorris.model.impl

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.common.game.Position
import com.kr8ne.mensMorris.common.game.utils.gameStartPosition
import com.kr8ne.mensMorris.data.impl.GameBoardData
import com.kr8ne.mensMorris.domain.impl.GameBoardScreen
import com.kr8ne.mensMorris.model.interfaces.ViewModelI

/**
 * view model for game board
 */
class GameBoardViewModel(
    /**
     * stores current position
     */
    pos: MutableState<Position> = mutableStateOf(gameStartPosition),
    /**
     * what will happen if we click some circle
     */
    onClick: (index: Int, func: (elementIndex: Int) -> Unit) -> Unit = { _, _ -> },
    /**
     * what we should additionally do on undo
     */
    onUndo: () -> Unit = {},
    /**
     * stores all pieces which can be moved (used for highlighting)
     */
    moveHints: MutableState<List<Int>> = mutableStateOf(listOf()),
    /**
     * used for storing info of the previous (valid one) clicked button
     */
    selectedButton: MutableState<Int?> = mutableStateOf(null),
    /**
     * navigation controller
     */
    navController: NavHostController?
) : ViewModelI() {
    constructor(
        pos: Position,
        onClick: (index: Int, func: (elementIndex: Int) -> Unit) -> Unit = { _, _ -> },
        onUndo: () -> Unit = {},
        moveHints: MutableState<List<Int>> = mutableStateOf(listOf()),
        selectedButton: MutableState<Int?> = mutableStateOf(null),
        navController: NavHostController?
    ) : this(mutableStateOf(pos), onClick, onUndo, moveHints, selectedButton, navController)

    override val data =
        GameBoardData(pos, moveHints, onUndo, onClick, selectedButton, navController)
    override var render = GameBoardScreen(
        data.pos,
        { index -> data.onClick(index) },
        { data.handleUndo() },
        { data.handleRedo() },
        { data.calculateAlpha(it) }
    )
}
