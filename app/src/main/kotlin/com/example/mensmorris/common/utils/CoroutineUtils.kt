package com.example.mensmorris.common.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * our dispatcher for starting data class in mvvvm
 */
val backendScope = Dispatchers.Default

/**
 * our dispatcher for starting bot in mvvvm
 */
val botScope = Dispatchers.Default

/**
 * our dispatcher for starting bot in mvvvm
 */
@OptIn(ExperimentalCoroutinesApi::class)
val liveDataScope = Dispatchers.Default.limitedParallelism(1)
