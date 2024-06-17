package com.kroune.mensMorris.viewModel.impl.tutorial

import com.kroune.mensMorris.data.local.impl.tutorial.TutorialData
import com.kroune.mensMorris.viewModel.interfaces.ViewModelI

/**
 * view model for tutorials
 */
class TutorialViewModel : ViewModelI() {
    override val data: TutorialData = TutorialData()
}
