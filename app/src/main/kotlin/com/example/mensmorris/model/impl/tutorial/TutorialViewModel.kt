package com.example.mensmorris.model.impl.tutorial

import androidx.lifecycle.ViewModel
import com.example.mensmorris.data.DataModel
import com.example.mensmorris.data.impl.tutorial.TutorialData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.tutorial.TutorialScreen
import com.example.mensmorris.model.ViewModelInterface

/**
 * view model for tutorials
 */
class TutorialViewModel : ViewModelInterface, ViewModel() {
    override var render: ScreenModel = TutorialScreen()
    override val data: DataModel = TutorialData(this)
}
