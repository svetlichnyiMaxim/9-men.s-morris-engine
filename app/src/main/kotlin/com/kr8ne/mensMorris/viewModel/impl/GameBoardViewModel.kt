package com.kr8ne.mensMorris.viewModel.impl

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.Position
import com.kr8ne.mensMorris.data.impl.GameBoardData
import com.kr8ne.mensMorris.gameStartPosition
import com.kr8ne.mensMorris.ui.impl.GameBoardScreen
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

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
    onClick: GameBoardData.(index: Int) -> Unit = { index ->
        handleClick(index)
        handleHighLighting()
    },
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
        onClick: GameBoardData.(index: Int) -> Unit = { },
        onUndo: () -> Unit = {},
        onRedo: () -> Unit = {},
        moveHints: MutableState<List<Int>> = mutableStateOf(listOf()),
        selectedButton: MutableState<Int?> = mutableStateOf(null),
        navController: NavHostController?
    ) : this(mutableStateOf(pos), onClick, onUndo, onRedo, moveHints, selectedButton, navController)

    override val data =
        GameBoardData(pos, moveHints, onUndo, onRedo, onClick, selectedButton, navController)
    override var render = GameBoardScreen(
        data.pos,
        { index -> data.onClick(index) },
        { data.handleUndo() },
        { data.handleRedo() },
        { data.calculateAlpha(it) }
    )
}
