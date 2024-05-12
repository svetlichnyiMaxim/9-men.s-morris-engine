package com.kr8ne.mensMorris.viewModel.impl.tutorial.viewModels

import com.kr8ne.mensMorris.data.impl.tutorial.data.IndicatorsTutorialData
import com.kr8ne.mensMorris.data.interfaces.DataModel
import com.kr8ne.mensMorris.ui.impl.tutorial.domain.IndicatorsTutorialScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * view model for tutorial on indicators
 */
class IndicatorsTutorialViewModel : ViewModelI() {
    override var render: ScreenModel = IndicatorsTutorialScreen()

    override val data: DataModel = IndicatorsTutorialData()
}

