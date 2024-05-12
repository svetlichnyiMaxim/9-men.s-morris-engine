package com.kr8ne.mensMorris.viewModel.impl.tutorial.viewModels

import com.kr8ne.mensMorris.data.impl.tutorial.data.FlyingMovesTutorialData
import com.kr8ne.mensMorris.data.interfaces.DataModel
import com.kr8ne.mensMorris.ui.impl.tutorial.domain.FlyingMovesTutorialScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * view model for tutorial on indicators
 */
class FlyingMovesTutorialViewModel : ViewModelI() {
    override var render: ScreenModel = FlyingMovesTutorialScreen()

    override val data: DataModel = FlyingMovesTutorialData()
}

