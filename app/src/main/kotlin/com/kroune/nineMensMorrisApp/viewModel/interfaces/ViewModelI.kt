package com.kroune.nineMensMorrisApp.viewModel.interfaces

import androidx.lifecycle.ViewModel
import com.kroune.nineMensMorrisApp.data.local.interfaces.DataI

/**
 * model for our view models
 */
abstract class ViewModelI : ViewModel() {

    /**
     * data we need
     */
    open val data: DataI
        get() = TODO("not implemented yet")
}
