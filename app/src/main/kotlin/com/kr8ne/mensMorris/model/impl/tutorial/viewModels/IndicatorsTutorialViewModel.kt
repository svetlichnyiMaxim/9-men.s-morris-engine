package com.kr8ne.mensMorris.model.impl.tutorial.viewModels

import com.kr8ne.mensMorris.data.impl.tutorial.data.IndicatorsTutorialData
import com.kr8ne.mensMorris.data.interfaces.DataModel
import com.kr8ne.mensMorris.domain.impl.tutorial.domain.IndicatorsTutorialScreen
import com.kr8ne.mensMorris.domain.interfaces.ScreenModel
import com.kr8ne.mensMorris.model.interfaces.ViewModelI

/**
 * view model for tutorial on indicators
 */
class IndicatorsTutorialViewModel : ViewModelI() {
    override var render: ScreenModel = IndicatorsTutorialScreen()

    override val data: DataModel = IndicatorsTutorialData()
}

