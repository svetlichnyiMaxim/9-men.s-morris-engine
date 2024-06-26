package com.kroune.nineMensMorrisApp.viewModel.impl

import com.kroune.nineMensMorrisApp.data.local.impl.AppStartAnimationData
import com.kroune.nineMensMorrisApp.viewModel.interfaces.ViewModelI

/**
 * ViewModel for the loading animation screen.
 */
class AppStartAnimationViewModel : ViewModelI() {
    override val data: AppStartAnimationData = AppStartAnimationData()
}
