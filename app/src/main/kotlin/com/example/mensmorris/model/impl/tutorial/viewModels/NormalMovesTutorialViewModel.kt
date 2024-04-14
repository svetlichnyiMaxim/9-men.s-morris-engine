package com.example.mensmorris.model.impl.tutorial.viewModels

import com.example.mensmorris.data.DataModel
import com.example.mensmorris.data.impl.tutorial.data.NormalMovesTutorialData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.tutorial.domain.NormalMovesTutorialScreen
import com.example.mensmorris.model.ViewModelI

/**
 * view model for tutorial on indicators
 */
class NormalMovesTutorialViewModel : ViewModelI() {

    override val data: DataModel = NormalMovesTutorialData(this)
    override var render: ScreenModel = NormalMovesTutorialScreen()
}

