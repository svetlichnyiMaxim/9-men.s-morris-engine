package com.kr8ne.mensMorris.viewModel.impl.tutorial

import com.kr8ne.mensMorris.data.impl.tutorial.TutorialData
import com.kr8ne.mensMorris.ui.impl.tutorial.TutorialScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * view model for tutorials
 */
class TutorialViewModel(progress: Float) : ViewModelI() {
    override val data: TutorialData = TutorialData(progress)
    override var render: ScreenModel = TutorialScreen(data.progress)
}
