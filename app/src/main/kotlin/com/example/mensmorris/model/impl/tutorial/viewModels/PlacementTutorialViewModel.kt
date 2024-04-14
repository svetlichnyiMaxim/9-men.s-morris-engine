package com.example.mensmorris.model.impl.tutorial.viewModels

import com.example.mensmorris.data.DataModel
import com.example.mensmorris.data.impl.tutorial.data.PlacementTutorialData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.tutorial.domain.PlacementTutorialScreen
import com.example.mensmorris.model.ViewModelI

/**
 * view model for tutorial on indicators
 */
class PlacementTutorialViewModel : ViewModelI() {

    override val data: DataModel = PlacementTutorialData(this)
    override var render: ScreenModel = PlacementTutorialScreen()
}

