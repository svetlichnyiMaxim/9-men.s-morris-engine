package com.kr8ne.mensMorris.data.local.impl.tutorial

import androidx.compose.runtime.mutableFloatStateOf
import com.kr8ne.mensMorris.data.local.interfaces.DataModel

/**
 * used for storing tutorials data (completion progress)
 */
class TutorialData(progress: Float) : DataModel {
    /**
     * z index of the tutorial screen (layer priority)
     */
    val progress = mutableFloatStateOf(progress)
}
