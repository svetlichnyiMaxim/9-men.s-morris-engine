package com.kroune.mensMorris.viewModel.impl

import com.kroune.mensMorris.data.local.impl.WelcomeData
import com.kroune.mensMorris.viewModel.interfaces.ViewModelI

/**
 * welcome model
 * called when app is launched
 */
class WelcomeViewModel : ViewModelI() {
    override val data = WelcomeData()
}
