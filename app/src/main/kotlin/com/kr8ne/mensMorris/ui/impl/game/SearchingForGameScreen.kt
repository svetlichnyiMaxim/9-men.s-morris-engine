package com.kr8ne.mensMorris.ui.impl.game

import android.content.res.Resources
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.R
import com.kr8ne.mensMorris.common.AppTheme
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.impl.game.SearchingForGameViewModel

/**
 * Game main screen
 */
class SearchingForGameScreen(val navController: NavHostController, val resources: Resources) :
    ScreenModel {
    @Composable
    override fun InvokeRender() {
        AppTheme {
            Text("${resources.getString(R.string.searching_for_game)}...")
        }
    }

    override val viewModel = SearchingForGameViewModel(navController)
}
