package com.kr8ne.mensMorris.data.impl.tutorial

import androidx.compose.runtime.mutableFloatStateOf
import com.kr8ne.mensMorris.data.interfaces.DataModel

/**
 * used for storing tutorials data (completion progress)
 */
class TutorialData : DataModel {
    /**
     * z index of the tutorial screen (layer priority)
     * @see com.kr8ne.mensMorris.model.impl.tutorial.viewModels.IndicatorsTutorialViewModel
     */
    val zIndex = mutableFloatStateOf(1f)

    /**
     * alpha of the tutorial screen
     * @see com.kr8ne.mensMorris.model.impl.tutorial.viewModels.IndicatorsTutorialViewModel
     */
    val alpha = mutableFloatStateOf(1f)
}
