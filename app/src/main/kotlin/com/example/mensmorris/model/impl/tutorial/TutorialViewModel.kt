package com.example.mensmorris.model.impl.tutorial

import androidx.lifecycle.ViewModel
import com.example.mensmorris.data.impl.tutorial.TutorialData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.tutorial.TutorialScreen
import com.example.mensmorris.model.ViewModelInterface

/**
 * view model for tutorials
 */
class TutorialViewModel : ViewModelInterface, ViewModel() {
    override val data: TutorialData = TutorialData(this)

    /**
     * alpha of the tutorial screen
     * @see com.example.mensmorris.domain.impl.WelcomeScreen
     */
    val alpha = data.alpha

    /**
     * z index of the tutorial screen (layer priority)
     * @see com.example.mensmorris.domain.impl.WelcomeScreen
     */
    val zIndex = data.zIndex
    override var render: ScreenModel = TutorialScreen(zIndex, alpha)
}
