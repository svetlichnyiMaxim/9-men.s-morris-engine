package com.example.mensmorris.model.impl

import androidx.compose.runtime.Composable
import com.example.mensmorris.data.impl.GameEndData
import com.example.mensmorris.domain.impl.GameEndScreen
import com.example.mensmorris.model.ModelModel
import com.example.mensmorris.utils.CoroutineUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class GameWithFriendModel : ModelModel {
    val render = GameEndScreen()
    val data = GameEndData()

    override fun invokeBackend() {
        CoroutineScope(CoroutineUtils.defaultDispatcher).async {
            data.invokeBackend()
        }
    }

    @Composable
    override fun invokeRender() {
        render.InvokeRender()
    }
}