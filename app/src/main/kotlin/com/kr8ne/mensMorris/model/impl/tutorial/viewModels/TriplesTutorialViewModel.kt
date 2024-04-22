package com.kr8ne.mensMorris.model.impl.tutorial.viewModels

import com.kr8ne.mensMorris.data.impl.tutorial.data.TriplesTutorialData
import com.kr8ne.mensMorris.data.interfaces.DataModel
import com.kr8ne.mensMorris.domain.impl.tutorial.domain.TriplesTutorialScreen
import com.kr8ne.mensMorris.domain.interfaces.ScreenModel
import com.kr8ne.mensMorris.model.interfaces.ViewModelI

/**
 * view model for tutorial on indicators
 */
class TriplesTutorialViewModel : ViewModelI() {

    override val data: DataModel = TriplesTutorialData()
    override var render: ScreenModel = TriplesTutorialScreen()
}

