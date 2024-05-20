package com.kr8ne.mensMorris.ui.impl

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.WELCOME_SCREEN
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import kotlin.math.sin

class LoadingAnimationScreen(
    val controller: NavHostController
) : ScreenModel {

    @Composable
    override fun InvokeRender() {
        DrawAnimation()
        StartButton()
    }

    @Composable
    fun DrawAnimation() {
        val infiniteScale = rememberInfiniteTransition(label = "backgroundAnimation")
        val animatedProgress by infiniteScale.animateFloat(
            initialValue = 1f,
            targetValue = -1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 7500,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ), label = "backgroundAnimation"
        )
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                // we need to overlap our prerender
                .zIndex(1f)
        ) {
            val finalPoint = f(size.width.toInt())
            val coef = size.height.toInt() / finalPoint
            for (x in 0 until size.width.toInt()) {
                val y = f(x, coef, animatedProgress)
                drawLine(
                    if (animatedProgress > 0f) Color.Gray else Color.DarkGray,
                    Offset(x.toFloat(), 0f),
                    Offset(x.toFloat(), size.height - y),
                    strokeWidth = 2f
                )
                drawLine(
                    if (animatedProgress < 0f) Color.Gray else Color.DarkGray,
                    Offset(x.toFloat(), size.height),
                    Offset(x.toFloat(), size.height - y),
                    strokeWidth = 2f
                )
            }
        }
    }

    @Composable
    fun StartButton() {
        val infiniteScale = rememberInfiniteTransition(label = "buttonAnimation")
        val animatedProgress by infiniteScale.animateFloat(
            initialValue = 1f,
            targetValue = 0.75f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1500,
                    easing = CubicBezierEasing(0.2f, 0.0f, 0.4f, 1.0f)
                ),
                repeatMode = RepeatMode.Reverse
            ), label = "buttonAnimation"
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
        ) {
            TextButton({
                controller.navigate(WELCOME_SCREEN)
            }, modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = "Press to start",
                    modifier = Modifier
                        .alpha(animatedProgress),
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 24.sp,
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(2.0f, 5.0f),
                            blurRadius = 2f
                        )
                    )
                )
            }
        }
    }


    fun f(x: Int, coef: Float = 1f, animationProgress: Float = 1f): Float {
        return (1.55f * x * animationProgress + 300 * sin(0.006f * x) / animationProgress) * coef
    }
}