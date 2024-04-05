package com.example.mensmorris.common.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * our dispatcher for most of the coroutines
 */
@OptIn(ExperimentalCoroutinesApi::class)
val defaultDispatcher = Dispatchers.IO//.limitedParallelism(10)
