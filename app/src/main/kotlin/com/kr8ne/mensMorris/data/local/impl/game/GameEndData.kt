package com.kr8ne.mensMorris.data.local.impl.game

import com.kr8ne.mensMorris.data.local.interfaces.DataI
import com.kr8ne.mensMorris.ui.impl.game.GameBoardScreen

/**
 * data for game end screen
 */
class GameEndData(
    /**
     * our game board
     */
    var gameBoard: GameBoardScreen
) : DataI()
