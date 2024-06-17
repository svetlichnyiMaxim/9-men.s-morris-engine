package com.kroune.mensMorris.viewModel.impl

import com.kroune.mensMorris.data.local.impl.AppStartAnimationData
import com.kroune.mensMorris.viewModel.interfaces.ViewModelI

/**
 * ViewModel for the loading animation screen.
 */
class AppStartAnimationViewModel : ViewModelI() {
    override val data: AppStartAnimationData = AppStartAnimationData()
}
