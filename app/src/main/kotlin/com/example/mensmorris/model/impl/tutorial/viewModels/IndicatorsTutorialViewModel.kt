package com.example.mensmorris.model.impl.tutorial.viewModels

import androidx.lifecycle.ViewModel
import com.example.mensmorris.data.DataModel
import com.example.mensmorris.data.impl.tutorial.data.IndicatorsTutorialData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.tutorial.domain.IndicatorsTutorialScreen
import com.example.mensmorris.model.ViewModelInterface

/**
 * view model for tutorial on indicators
 */
class IndicatorsTutorialViewModel :
    ViewModelInterface, ViewModel() {
    override var render: ScreenModel = IndicatorsTutorialScreen()

    override val data: DataModel = IndicatorsTutorialData(this)
}

