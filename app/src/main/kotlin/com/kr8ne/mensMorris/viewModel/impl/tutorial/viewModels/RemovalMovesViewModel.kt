package com.kr8ne.mensMorris.viewModel.impl.tutorial.viewModels

import com.kr8ne.mensMorris.data.impl.tutorial.data.RemovalMovesTutorialData
import com.kr8ne.mensMorris.data.interfaces.DataModel
import com.kr8ne.mensMorris.ui.impl.tutorial.domain.RemovalMovesTutorialScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * view model for tutorial on indicators
 */
class RemovalMovesViewModel : ViewModelI() {

    override val data: DataModel = RemovalMovesTutorialData()
    override var render: ScreenModel = RemovalMovesTutorialScreen()
}

