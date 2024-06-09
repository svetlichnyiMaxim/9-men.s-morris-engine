package com.kr8ne.mensMorris.viewModel.impl.tutorial

import android.content.SharedPreferences
import android.content.res.Resources
import com.kr8ne.mensMorris.data.local.impl.tutorial.TutorialData
import com.kr8ne.mensMorris.ui.impl.tutorial.TutorialScreen
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.interfaces.ViewModelI

/**
 * view model for tutorials
 */
class TutorialViewModel : ViewModelI() {
    override val data: TutorialData = TutorialData()
}
