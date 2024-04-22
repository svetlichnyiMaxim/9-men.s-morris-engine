package com.kr8ne.mensMorris.model.impl.tutorial

import com.kr8ne.mensMorris.data.impl.tutorial.TutorialData
import com.kr8ne.mensMorris.domain.impl.tutorial.TutorialScreen
import com.kr8ne.mensMorris.domain.interfaces.ScreenModel
import com.kr8ne.mensMorris.model.interfaces.ViewModelI

/**
 * view model for tutorials
 */
class TutorialViewModel : ViewModelI() {
    override val data: TutorialData = TutorialData()

    /**
     * alpha of the tutorial screen
     * @see com.kr8ne.mensMorris.domain.impl.WelcomeScreen
     */
    val alpha = data.alpha

    /**
     * z index of the tutorial screen (layer priority)
     * @see com.kr8ne.mensMorris.domain.impl.WelcomeScreen
     */
    val zIndex = data.zIndex
    override var render: ScreenModel = TutorialScreen(zIndex, alpha)
}
