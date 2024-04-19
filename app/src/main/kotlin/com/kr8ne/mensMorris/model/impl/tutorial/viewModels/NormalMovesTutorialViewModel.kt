package com.kr8ne.mensMorris.model.impl.tutorial.viewModels

import com.kr8ne.mensMorris.data.DataModel
import com.kr8ne.mensMorris.data.impl.tutorial.data.NormalMovesTutorialData
import com.kr8ne.mensMorris.domain.ScreenModel
import com.kr8ne.mensMorris.domain.impl.tutorial.domain.NormalMovesTutorialScreen
import com.kr8ne.mensMorris.model.ViewModelI

/**
 * view model for tutorial on indicators
 */
class NormalMovesTutorialViewModel : ViewModelI() {

    override val data: DataModel = NormalMovesTutorialData(this)
    override var render: ScreenModel = NormalMovesTutorialScreen()
}

