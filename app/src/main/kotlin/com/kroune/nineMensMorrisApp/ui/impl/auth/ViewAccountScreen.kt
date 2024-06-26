package com.kroune.nineMensMorrisApp.ui.impl.auth

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.kroune.nineMensMorrisApp.common.AppTheme
import com.kroune.nineMensMorrisApp.ui.interfaces.ScreenModel
import com.kroune.nineMensMorrisApp.viewModel.impl.auth.ViewAccountViewModel

/**
 * account view screen
 * @param id id of the account
 */
class ViewAccountScreen(
    val id: Long
) : ScreenModel {
    override lateinit var viewModel: ViewAccountViewModel

    @Composable
    override fun InvokeRender() {
        viewModel = hiltViewModel()
        AppTheme {
            Text("not implemented yet")
        }
    }
}
