package com.kr8ne.mensMorris.model.impl

import com.kr8ne.mensMorris.data.impl.WelcomeData
import com.kr8ne.mensMorris.domain.ScreenModel
import com.kr8ne.mensMorris.domain.impl.WelcomeScreen
import com.kr8ne.mensMorris.model.ViewModelI

/**
 * welcome model
 * called when app is launched
 */
class WelcomeViewModel : ViewModelI() {
    override var render: ScreenModel = WelcomeScreen()
    override val data = WelcomeData(this)
}
