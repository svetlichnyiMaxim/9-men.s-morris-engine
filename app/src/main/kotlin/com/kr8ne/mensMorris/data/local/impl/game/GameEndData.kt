package com.kr8ne.mensMorris.data.local.impl.game

import com.kr8ne.mensMorris.data.local.interfaces.DataModel
import com.kr8ne.mensMorris.viewModel.impl.game.GameBoardViewModel

/**
 * data for game end screen
 */
class GameEndData(
    /**
     * our game board
     */
    var gameBoard: GameBoardViewModel
) : DataModel
