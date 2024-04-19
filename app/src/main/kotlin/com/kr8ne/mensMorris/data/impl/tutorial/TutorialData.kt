package com.kr8ne.mensMorris.data.impl.tutorial

import androidx.compose.runtime.mutableFloatStateOf
import androidx.lifecycle.ViewModel
import com.kr8ne.mensMorris.data.DataModel

/**
 * used for storing tutorials data (completion progress)
 */
class TutorialData(override val viewModel: ViewModel) : DataModel {
    /**
     * z index of the tutorial screen (layer priority)
     * @see com.kr8ne.mensMorris.model.impl.tutorial.IndicatorsTutorialViewModel
     */
    val zIndex = mutableFloatStateOf(1f)

    /**
     * alpha of the tutorial screen
     * @see com.kr8ne.mensMorris.model.impl.tutorial.IndicatorsTutorialViewModel
     */
    val alpha = mutableFloatStateOf(1f)
}
