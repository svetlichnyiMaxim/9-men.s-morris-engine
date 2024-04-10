package com.example.mensmorris.model.impl.tutorial

import com.example.mensmorris.model.ViewModelInterface

/**
 * model for our view models
 */
interface TutorialViewModelInterface : ViewModelInterface {
    /**
     * function that switches to the next screen
     * TODO: check if it should be inlined
     */
    val switchToNextScreen: () -> Unit
}
