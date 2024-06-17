package com.kroune.mensMorris.ui.impl.game

import android.content.res.Resources
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.kroune.mensMorris.R
import com.kroune.mensMorris.common.AppTheme
import com.kroune.mensMorris.ui.interfaces.ScreenModel
import com.kroune.mensMorris.viewModel.impl.game.SearchingForGameViewModel

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
