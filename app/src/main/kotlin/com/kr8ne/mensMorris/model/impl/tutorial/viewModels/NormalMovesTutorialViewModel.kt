package com.kr8ne.mensMorris.model.impl.tutorial.viewModels

import com.kr8ne.mensMorris.data.impl.tutorial.data.NormalMovesTutorialData
import com.kr8ne.mensMorris.data.interfaces.DataModel
import com.kr8ne.mensMorris.domain.impl.tutorial.domain.NormalMovesTutorialScreen
import com.kr8ne.mensMorris.domain.interfaces.ScreenModel
import com.kr8ne.mensMorris.model.interfaces.ViewModelI

/**
 * view model for tutorial on indicators
 */
class NormalMovesTutorialViewModel : ViewModelI() {

    override val data: DataModel = NormalMovesTutorialData()
    override var render: ScreenModel = NormalMovesTutorialScreen()
}

