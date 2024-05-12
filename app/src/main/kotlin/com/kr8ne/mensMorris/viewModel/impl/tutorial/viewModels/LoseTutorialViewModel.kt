package com.kr8ne.mensMorris.viewModel.impl.tutorial.viewModels

import com.kr8ne.mensMorris.data.impl.tutorial.data.LoseTutorialData
import com.kr8ne.mensMorris.data.interfaces.DataModel
import com.kr8ne.mensMorris.ui.impl.tutorial.domain.LoseTutorialScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * view model for tutorial on indicators
 */
class LoseTutorialViewModel : ViewModelI() {

    override val data: DataModel = LoseTutorialData()
    override var render: ScreenModel = LoseTutorialScreen()
}

