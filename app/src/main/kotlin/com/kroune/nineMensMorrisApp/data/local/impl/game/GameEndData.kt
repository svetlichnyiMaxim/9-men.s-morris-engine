package com.kroune.nineMensMorrisApp.data.local.impl.game

import com.kroune.nineMensMorrisApp.data.local.interfaces.DataI
import com.kroune.nineMensMorrisApp.ui.impl.game.GameBoardScreen

/**
 * data for game end screen
 */
class GameEndData(
    /**
     * our game board
     */
    var gameBoard: GameBoardScreen
) : DataI()
