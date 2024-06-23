package com.kroune.mensMorris.ui.impl.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.kroune.mensMorris.common.AppTheme
import com.kroune.mensMorris.ui.interfaces.ScreenModel
import com.kroune.mensMorris.viewModel.impl.auth.ViewAccountViewModel

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
            if (id == -1L) {
                Column {
                    Text("Login - ${viewModel.getLoginById(id)}")
                }
            } else {
                Text("not your account, not implemented")
            }
        }
    }
}
