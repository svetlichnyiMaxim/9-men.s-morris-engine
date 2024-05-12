package com.kr8ne.mensMorris.data.impl

import com.kr8ne.mensMorris.data.interfaces.DataModel
import com.kr8ne.mensMorris.viewModel.impl.GameBoardViewModel

/**
 * data for game end screen
 */
class GameEndData(
    /**
     * our game board
     */
    var gameBoard: GameBoardViewModel
) : DataModel
