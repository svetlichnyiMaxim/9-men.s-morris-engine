package com.kr8ne.mensMorris.model.impl.tutorial.viewModels

import com.kr8ne.mensMorris.data.DataModel
import com.kr8ne.mensMorris.data.impl.tutorial.data.TriplesTutorialData
import com.kr8ne.mensMorris.domain.ScreenModel
import com.kr8ne.mensMorris.domain.impl.tutorial.domain.TriplesTutorialScreen
import com.kr8ne.mensMorris.model.ViewModelI

/**
 * view model for tutorial on indicators
 */
class TriplesTutorialViewModel : ViewModelI() {

    override val data: DataModel = TriplesTutorialData(this)
    override var render: ScreenModel = TriplesTutorialScreen()
}

