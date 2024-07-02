package com.kroune.nineMensMorrisApp.ui.impl

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.kroune.nineMensMorrisApp.Navigation
import com.kroune.nineMensMorrisApp.navigateSingleTopTo
import com.kroune.nineMensMorrisApp.ui.interfaces.ScreenModelI
import kotlin.math.sin

/**
 * this screen is shown at the start of the app
 * data loading should start here
 */
class AppStartAnimationScreen(
    /**
     * navigation controller
     */
    private val navController: NavHostController?
) : ScreenModelI {

    @Composable
    override fun InvokeRender() {
        DrawAnimation()
        StartButton()
    }

    /**
     * draw good looking animation
     * can be used when loading data
     */
    @Composable
    fun DrawAnimation() {
        val animatedProgress by rememberInfiniteTransition(label = "").animateFloat(
            initialValue = 1f,
            targetValue = -1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 7500, easing = LinearEasing
                ), repeatMode = RepeatMode.Reverse
            ),
            label = "backgroundAnimation"
        )
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(if (animatedProgress <= 0f) Color.DarkGray else Color.LightGray)
                .zIndex(1f)
        ) {
            val finalPoint = f(size.width.toInt())
            val coef = size.height.toInt() / finalPoint
            val lightPath = Path()
            for (x in 0 until size.width.toInt() step 10) {
                val y = f(x, coef, animatedProgress).coerceIn(0f..size.height)
                lightPath.lineTo(x.toFloat(), size.height - y)
            }
            val y = f(size.width.toInt(), coef, animatedProgress).coerceIn(0f..size.height)
            lightPath.lineTo(size.width, size.height - y)
            lightPath.lineTo(size.width, size.height)
            lightPath.lineTo(0f, size.height)
            drawPath(
                path = lightPath,
                color = if (animatedProgress > 0f) Color.DarkGray else Color.LightGray
            )
        }
    }

    /**
     * draws animation for the start button
     * it should be transparent if some important operation is in progress
     */
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
            ),
            label = "buttonAnimation"
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
        ) {
            TextButton(
                {
                    navController?.navigateSingleTopTo(Navigation.Welcome)
                },
                modifier = Modifier.align(Alignment.Center),
                colors = ButtonColors(
                    Color.Transparent,
                    Color.Transparent,
                    Color.Transparent,
                    Color.Transparent
                )
            ) {
                Text(
                    text = "Press to start",
                    modifier = Modifier.alpha(animatedProgress),
                    color = Color.White
                )
            }
        }
    }

    /**
     * this is basically 1.55*x*a+300*sin(0.005*x)/a
     *
     * @param x - x coordinate
     * @param multi - we use it to make our curve end at the top bottom of the screen
     * @param animationProgress - progress of the animation
     */
    private fun f(x: Int, multi: Float = 1f, animationProgress: Float = 1f): Float {
        return (1.55f * x * animationProgress + 300 * sin(0.005f * x) / animationProgress) * multi
    }
}
