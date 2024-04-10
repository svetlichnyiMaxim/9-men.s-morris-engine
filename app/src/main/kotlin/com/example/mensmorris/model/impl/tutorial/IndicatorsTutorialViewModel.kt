package com.example.mensmorris.model.impl.tutorial

import com.example.mensmorris.data.DataModel
import com.example.mensmorris.data.impl.tutorial.IndicatorsTutorialData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.model.ViewModelInterface

class IndicatorsTutorialViewModel : ViewModelInterface {
    override var render: ScreenModel
        get() = TODO("Not yet implemented")
        set(value) {}

    override val data: DataModel = IndicatorsTutorialData()
}