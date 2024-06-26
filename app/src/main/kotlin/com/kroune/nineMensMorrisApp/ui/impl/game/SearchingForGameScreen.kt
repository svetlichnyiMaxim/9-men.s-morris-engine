package com.kroune.nineMensMorrisApp.ui.impl.game

import android.content.res.Resources
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.kroune.nineMensMorrisApp.R
import com.kroune.nineMensMorrisApp.common.AppTheme
import com.kroune.nineMensMorrisApp.ui.interfaces.ScreenModel
import com.kroune.nineMensMorrisApp.viewModel.impl.game.SearchingForGameViewModel

/**
 * Game main screen
 */
class SearchingForGameScreen(
    navController: NavHostController,
    private val resources: Resources
) : ScreenModel {
    @Composable
    override fun InvokeRender() {
        AppTheme {
            Text("${resources.getString(R.string.searching_for_game)}...")
        }
    }

    override val viewModel = SearchingForGameViewModel(navController)
}
