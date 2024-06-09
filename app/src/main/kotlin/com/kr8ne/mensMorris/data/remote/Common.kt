package com.kr8ne.mensMorris.data.remote

import androidx.core.content.edit
import com.kr8ne.mensMorris.activity
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.websocket.WebSockets
import kotlinx.coroutines.Dispatchers

/**
 * Jwt token provided by the server
 */
@Volatile
var jwtToken: String? = activity?.sharedPreferences?.getString("jwtToken", null)
    set(value) {
        field = value
        activity?.sharedPreferences?.edit(commit = true) {
            putString("jwtToken", value).apply()
        }
    }

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
