package com.kr8ne.mensMorris

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.unit.dp
import com.kr8ne.mensMorris.model.impl.WelcomeViewModel

/**
 * shows how thick our pieces & board will be
 */
val BUTTON_WIDTH = 35.dp

/**
 * represents current activity
 * used for switching screens
 */
lateinit var activity: ComponentActivity

/**
 * activity our app is launched from
 */
class MainActivity : ComponentActivity() {
    /**
     * we initialize all important stuff here
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        setContent {
            WelcomeViewModel().Invoke()
        }
    }
}
