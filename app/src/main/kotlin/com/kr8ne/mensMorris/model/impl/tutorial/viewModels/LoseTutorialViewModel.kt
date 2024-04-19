package com.kr8ne.mensMorris.model.impl.tutorial.viewModels

import com.kr8ne.mensMorris.data.DataModel
import com.kr8ne.mensMorris.data.impl.tutorial.data.LoseTutorialData
import com.kr8ne.mensMorris.domain.ScreenModel
import com.kr8ne.mensMorris.domain.impl.tutorial.domain.LoseTutorialScreen
import com.kr8ne.mensMorris.model.ViewModelI

/**
 * view model for tutorial on indicators
 */
class LoseTutorialViewModel : ViewModelI() {

    override val data: DataModel = LoseTutorialData(this)
    override var render: ScreenModel = LoseTutorialScreen()
}

