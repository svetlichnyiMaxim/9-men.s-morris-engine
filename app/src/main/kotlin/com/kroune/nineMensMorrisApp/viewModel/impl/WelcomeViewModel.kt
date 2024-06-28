package com.kroune.nineMensMorrisApp.viewModel.impl

import com.kroune.nineMensMorrisApp.data.remote.auth.AuthRepositoryI
import com.kroune.nineMensMorrisApp.viewModel.interfaces.ViewModelI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * welcome model
 * called when app is launched
 */
@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val authRepositoryI: AuthRepositoryI
) : ViewModelI() {
    /**
     * checks if jwt token is valid
     */
    suspend fun checkJwtToken(): Result<Boolean> {
        return authRepositoryI.checkJwtToken()
    }
}
