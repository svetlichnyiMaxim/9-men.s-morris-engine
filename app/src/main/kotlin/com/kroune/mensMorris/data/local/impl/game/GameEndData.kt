package com.kroune.mensMorris.data.local.impl.game

import com.kroune.mensMorris.data.local.interfaces.DataI
import com.kroune.mensMorris.ui.impl.game.GameBoardScreen

/**
 * data for game end screen
 */
class GameEndData(
    /**
     * our game board
     */
    var gameBoard: GameBoardScreen
) : DataI()
