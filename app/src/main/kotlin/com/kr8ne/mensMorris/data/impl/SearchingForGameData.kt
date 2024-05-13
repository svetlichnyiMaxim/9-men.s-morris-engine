package com.kr8ne.mensMorris.data.impl

import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.api.Client
import com.kr8ne.mensMorris.data.interfaces.DataModel

/**
 * data for game end screen
 */
class SearchingForGameData(
    /**
     * The NavHostController instance used for navigation.
     */
    val navController: NavHostController
) : DataModel {
    override suspend fun invokeBackend() {
        //Client.startSearchingGame()
    }
}
