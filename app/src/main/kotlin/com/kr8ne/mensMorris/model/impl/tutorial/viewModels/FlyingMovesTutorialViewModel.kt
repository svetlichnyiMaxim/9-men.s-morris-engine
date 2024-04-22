package com.kr8ne.mensMorris.model.impl.tutorial.viewModels

import com.kr8ne.mensMorris.data.impl.tutorial.data.FlyingMovesTutorialData
import com.kr8ne.mensMorris.data.interfaces.DataModel
import com.kr8ne.mensMorris.domain.impl.tutorial.domain.FlyingMovesTutorialScreen
import com.kr8ne.mensMorris.domain.interfaces.ScreenModel
import com.kr8ne.mensMorris.model.interfaces.ViewModelI

/**
 * view model for tutorial on indicators
 */
class FlyingMovesTutorialViewModel : ViewModelI() {
    override var render: ScreenModel = FlyingMovesTutorialScreen()

    override val data: DataModel = FlyingMovesTutorialData()
}

