package com.example.mensmorris.model.impl.tutorial.viewModels

import androidx.lifecycle.ViewModel
import com.example.mensmorris.data.DataModel
import com.example.mensmorris.data.impl.tutorial.data.LoseTutorialData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.tutorial.domain.LoseTutorialScreen
import com.example.mensmorris.model.ViewModelInterface

/**
 * view model for tutorial on indicators
 */
class LoseTutorialViewModel :
    ViewModelInterface, ViewModel() {

    override val data: DataModel = LoseTutorialData(this)
    override var render: ScreenModel = LoseTutorialScreen()
}

