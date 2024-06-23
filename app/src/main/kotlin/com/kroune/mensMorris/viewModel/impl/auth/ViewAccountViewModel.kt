package com.kroune.mensMorris.viewModel.impl.auth

import com.kroune.mensMorris.data.remote.account.AccountInfoRepositoryI
import com.kroune.mensMorris.viewModel.interfaces.ViewModelI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * view model for viewing account
 */
@HiltViewModel
class ViewAccountViewModel @Inject constructor(private val accountInfoRepositoryI: AccountInfoRepositoryI) :
    ViewModelI() {

    /**
     * gets login by id
     */
    fun getLoginById(id: Long): String {
        return accountInfoRepositoryI.getAccountLoginById(id)
    }
}
