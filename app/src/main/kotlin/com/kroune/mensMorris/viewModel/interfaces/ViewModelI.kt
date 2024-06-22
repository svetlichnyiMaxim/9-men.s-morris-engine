package com.kroune.mensMorris.viewModel.interfaces

import androidx.lifecycle.ViewModel
import com.kroune.mensMorris.data.local.interfaces.DataI

/**
 * model for our view models
 */
abstract class ViewModelI : ViewModel() {

    /**
     * data we need
     */
    abstract val data: DataI
}