package com.example.mensmorris.model

import androidx.compose.runtime.Composable

// TODO: rename this
interface ModelModel {
    fun invokeBackend()

    @Composable
    fun invokeRender()
}