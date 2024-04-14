package com.example.mensmorris.model.impl.tutorial.viewModels

import com.example.mensmorris.data.DataModel
import com.example.mensmorris.data.impl.tutorial.data.LoseTutorialData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.tutorial.domain.LoseTutorialScreen
import com.example.mensmorris.model.ViewModelI

/**
 * view model for tutorial on indicators
 */
class LoseTutorialViewModel : ViewModelI() {

    override val data: DataModel = LoseTutorialData(this)
    override var render: ScreenModel = LoseTutorialScreen()
}

