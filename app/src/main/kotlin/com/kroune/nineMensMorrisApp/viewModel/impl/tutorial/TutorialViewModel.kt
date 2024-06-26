package com.kroune.nineMensMorrisApp.viewModel.impl.tutorial

import com.kroune.nineMensMorrisApp.data.local.impl.tutorial.TutorialData
import com.kroune.nineMensMorrisApp.viewModel.interfaces.ViewModelI

/**
 * view model for tutorials
 */
class TutorialViewModel : ViewModelI() {
    override val data: TutorialData = TutorialData()
}
