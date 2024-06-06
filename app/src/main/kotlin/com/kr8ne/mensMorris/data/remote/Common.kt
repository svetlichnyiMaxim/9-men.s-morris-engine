package com.kr8ne.mensMorris.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.websocket.WebSockets
import kotlinx.coroutines.Dispatchers

/**
 * The network scope for asynchronous operations.
 */
val networkScope = Dispatchers.IO

/**
 * The network client for making HTTP requests.
 */
val network = HttpClient(OkHttp) {
    install(WebSockets)
}
