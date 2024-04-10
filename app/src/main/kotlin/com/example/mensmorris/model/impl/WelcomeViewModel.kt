package com.example.mensmorris.model.impl

import androidx.lifecycle.ViewModel
import com.example.mensmorris.data.impl.WelcomeData
import com.example.mensmorris.domain.ScreenModel
import com.example.mensmorris.domain.impl.WelcomeScreen
import com.example.mensmorris.model.ViewModelInterface

/**
 * welcome model
 * called when app is launched
 */
class WelcomeViewModel : ViewModelInterface, ViewModel() {
    override var render: ScreenModel = WelcomeScreen()
    override val data = WelcomeData(this)
}
