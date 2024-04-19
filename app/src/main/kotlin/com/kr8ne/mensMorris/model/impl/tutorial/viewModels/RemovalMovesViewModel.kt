package com.kr8ne.mensMorris.model.impl.tutorial.viewModels

import com.kr8ne.mensMorris.data.DataModel
import com.kr8ne.mensMorris.data.impl.tutorial.data.RemovalMovesTutorialData
import com.kr8ne.mensMorris.domain.ScreenModel
import com.kr8ne.mensMorris.domain.impl.tutorial.domain.RemovalMovesTutorialScreen
import com.kr8ne.mensMorris.model.ViewModelI

/**
 * view model for tutorial on indicators
 */
class RemovalMovesViewModel : ViewModelI() {

    override val data: DataModel = RemovalMovesTutorialData(this)
    override var render: ScreenModel = RemovalMovesTutorialScreen()
}

