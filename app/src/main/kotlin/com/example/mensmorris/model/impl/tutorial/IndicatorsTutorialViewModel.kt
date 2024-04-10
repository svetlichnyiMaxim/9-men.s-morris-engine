package com.example.mensmorris.model.impl.tutorial

import androidx.lifecycle.ViewModel
import com.example.mensmorris.data.DataModel
import com.example.mensmorris.data.impl.tutorial.IndicatorsTutorialData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.tutorial.IndicatorsTutorialScreen

/**
 * view model for tutorial on indicators
 */
class IndicatorsTutorialViewModel(override val switchToNextScreen: () -> Unit) :
    TutorialViewModelInterface, ViewModel() {
    override var render: ScreenModel = IndicatorsTutorialScreen()

    override val data: DataModel = IndicatorsTutorialData(this)
}

