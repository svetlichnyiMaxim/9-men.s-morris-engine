package com.kroune.mensMorris.data.remote

import android.content.SharedPreferences
import androidx.core.content.edit
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.websocket.WebSockets
import kotlinx.coroutines.Dispatchers

/**
 * TODO: Move this to remote repository
 */
object Common {
    /**
     * shared preferences used for storing jwt token and other important things
     * TODO: move this to repository
     */
    var sharedPreferences: SharedPreferences? = null

    /**
     * Jwt token provided by the server
     */
    @Volatile
    var jwtToken: String? = sharedPreferences?.getString("jwtToken", null)
        set(value) {
            field = value
            sharedPreferences?.edit(commit = true) {
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
}
