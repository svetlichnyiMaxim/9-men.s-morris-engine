package com.kroune.nineMensMorrisApp.ui.impl.auth

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kroune.nineMensMorrisApp.R
import com.kroune.nineMensMorrisApp.common.AppTheme
import com.kroune.nineMensMorrisApp.ui.interfaces.ScreenModel
import com.kroune.nineMensMorrisApp.viewModel.impl.auth.ViewAccountViewModel

/**
 * account view screen
 * @param id id of the account
 */
class ViewAccountScreen(
    val id: Long
) : ScreenModel {
    override lateinit var viewModel: ViewAccountViewModel

    @Composable
    override fun InvokeRender() {
        viewModel = hiltViewModel()
        AppTheme {
            Column {
                Row(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_account_circle_48),
                        contentDescription = "profile icon",
                        modifier = Modifier
                            .size(80.dp)
                    )
                    DrawName()
                }
                // TODO: make api calls
                Text("Account was registered at 27.06.2024", fontSize = 16.sp)
            }
        }
    }

    /**
     * draws user name or loading animation
     */
    @Composable
    fun DrawName() {
        val loadingName = false
        if (loadingName) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val animatedProgress by rememberInfiniteTransition(label = "").animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = 1000, easing = LinearEasing
                        ), repeatMode = RepeatMode.Reverse
                    ),
                    label = ""
                )
                CircularProgressIndicator(
                    progress = { animatedProgress },
                    gapSize = 0.dp
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // TODO: make api calls
                Text("Mike", fontSize = 20.sp)
            }
        }
    }
}
