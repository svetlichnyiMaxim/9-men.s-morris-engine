package com.example.mensmorris.model.impl

import androidx.compose.runtime.Composable
import com.example.mensmorris.data.impl.GameEndData
import com.example.mensmorris.data.impl.GameWithBotData
import com.example.mensmorris.domain.impl.GameEndScreen
import com.example.mensmorris.domain.impl.GameWithBotScreen
import com.example.mensmorris.model.ModelModel
import com.example.mensmorris.utils.CoroutineUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class GameWithBotModel : ModelModel {
    val render = GameWithBotScreen()
    val data = GameWithBotData()

    override fun invokeBackend() {
        CoroutineScope(CoroutineUtils.defaultDispatcher).async {
            data.invokeBackend()
        }
    }

    @Composable
    override fun invokeRender() {
        render.InvokeRender()
    }

    fun onClick(index: Int, func: () -> response(index, func)) {

    }
}