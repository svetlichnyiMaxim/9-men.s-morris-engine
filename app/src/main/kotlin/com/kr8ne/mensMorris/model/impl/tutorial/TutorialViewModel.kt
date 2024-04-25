package com.kr8ne.mensMorris.model.impl.tutorial

import com.kr8ne.mensMorris.data.impl.tutorial.TutorialData
import com.kr8ne.mensMorris.domain.impl.tutorial.TutorialScreen
import com.kr8ne.mensMorris.domain.interfaces.ScreenModel
import com.kr8ne.mensMorris.model.interfaces.ViewModelI

/**
 * view model for tutorials
 */
class TutorialViewModel(progress: Float) : ViewModelI() {
    override val data: TutorialData = TutorialData(progress)
    override var render: ScreenModel = TutorialScreen(data.progress)
}
