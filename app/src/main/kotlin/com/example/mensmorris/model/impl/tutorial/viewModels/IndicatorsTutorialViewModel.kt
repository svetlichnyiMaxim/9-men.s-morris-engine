package com.example.mensmorris.model.impl.tutorial.viewModels

import com.example.mensmorris.data.DataModel
import com.example.mensmorris.data.impl.tutorial.data.IndicatorsTutorialData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.tutorial.domain.IndicatorsTutorialScreen
import com.example.mensmorris.model.ViewModelI

/**
 * view model for tutorial on indicators
 */
class IndicatorsTutorialViewModel : ViewModelI() {
    override var render: ScreenModel = IndicatorsTutorialScreen()

    override val data: DataModel = IndicatorsTutorialData(this)
}

