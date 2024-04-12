package com.example.mensmorris.data.impl.tutorial

import androidx.compose.runtime.mutableFloatStateOf
import androidx.lifecycle.ViewModel
import com.example.mensmorris.data.DataModel

/**
 * used for storing tutorials data (completion progress)
 */
class TutorialData(override val viewModel: ViewModel) : DataModel {
    /**
     * z index of the tutorial screen (layer priority)
     * @see com.example.mensmorris.model.impl.tutorial.IndicatorsTutorialViewModel
     */
    val zIndex = mutableFloatStateOf(1f)

    /**
     * alpha of the tutorial screen
     * @see com.example.mensmorris.model.impl.tutorial.IndicatorsTutorialViewModel
     */
    val alpha = mutableFloatStateOf(1f)
}
