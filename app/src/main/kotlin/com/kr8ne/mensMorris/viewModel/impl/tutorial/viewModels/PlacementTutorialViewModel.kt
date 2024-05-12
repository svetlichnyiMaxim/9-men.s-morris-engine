package com.kr8ne.mensMorris.viewModel.impl.tutorial.viewModels

import com.kr8ne.mensMorris.data.impl.tutorial.data.PlacementTutorialData
import com.kr8ne.mensMorris.data.interfaces.DataModel
import com.kr8ne.mensMorris.ui.impl.tutorial.domain.PlacementTutorialScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * view model for tutorial on indicators
 */
class PlacementTutorialViewModel : ViewModelI() {

    override val data: DataModel = PlacementTutorialData()
    override var render: ScreenModel = PlacementTutorialScreen()
}

