package com.kr8ne.mensMorris.ui.impl

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kr8ne.mensMorris.common.utils.AppTheme
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import kotlin.math.sqrt

/**
 * Game main screen
 */
class SearchingForGameScreen : ScreenModel {
    private val distanceFromCenter = 50.0

    @Composable
    override fun InvokeRender() {
        val xOffset by remember {
            derivedStateOf { (1000 - System.currentTimeMillis() % 1000).toInt() }
        }
        // x^2+y^2=r^2
        // y = sqrt(r^2-x^2)
        val offset by animateIntOffsetAsState(
            targetValue = IntOffset(
                xOffset,
                sqrt(distanceFromCenter * distanceFromCenter - xOffset * xOffset).toInt()
            ),
            label = "offset"
        )
        AppTheme {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(Color.Blue)
                    .clip(CircleShape)
                    .offset {
                        offset
                    },
            )
        }
    }
}
