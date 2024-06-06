package com.kr8ne.mensMorris.ui.impl.game

import android.content.res.Resources
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kr8ne.mensMorris.R
import com.kr8ne.mensMorris.common.AppTheme
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel

/**
 * Game main screen
 */
class SearchingForGameScreen(val resources: Resources) : ScreenModel {
    @Composable
    override fun InvokeRender() {
        AppTheme {
            Text("${resources.getString(R.string.searching_for_game)}...")
        }
    }
}
