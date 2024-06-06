package com.kr8ne.mensMorris.ui.impl.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.R
import com.kr8ne.mensMorris.SEARCHING_ONLINE_GAME_SCREEN
import com.kr8ne.mensMorris.SIGN_UP_SCREEN
import com.kr8ne.mensMorris.common.AppTheme
import com.kr8ne.mensMorris.data.remote.Auth
import com.kr8ne.mensMorris.data.remote.Auth.jwtToken
import com.kr8ne.mensMorris.data.remote.ServerResponse
import com.kr8ne.mensMorris.data.remote.networkScope
import com.kr8ne.mensMorris.getString
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.SocketException

/**
 * Represents a screen for signing in to the application.
 *
 * @param navController The navigation controller for navigating between screens.
 * @param loginValidator A function that validates the provided login.
 * @param passwordValidator A function that validates the provided password.
 */
class SignInScreen(
    /**
     * navigation controller
     */
    val navController: NavHostController?,
    /**
     * Validates the provided login.
     *
     * @return True if the login is valid, false otherwise.
     */
    val loginValidator: (String) -> Boolean,
    /**
     * Validates the provided password.
     *
     * @return True if the password is valid, false otherwise.
     */
    val passwordValidator: (String) -> Boolean
) : ScreenModel {
    @Composable
    override fun InvokeRender() {
        var isSwitchingScreens = false
        AppTheme {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val serverResponse = remember { mutableStateOf<Result<String>?>(null) }
                val requestInProcess = remember { mutableStateOf(false) }
                val isUsernameValid = remember { mutableStateOf(false) }
                val username = remember { mutableStateOf("") }
                serverResponse.value?.onFailure { exception ->
                    when (exception) {
                        is ServerResponse.WrongPasswordOrLogin -> {
                            Text(text = getString(R.string.wrong_pass_or_login))
                        }

                        is ServerResponse.Unreachable, is SocketException -> {
                            Text(text = getString(R.string.network_error))
                        }

                        else -> {
                            Text(text = getString(R.string.server_error))
                        }
                    }
                }
                serverResponse.value?.onSuccess {
                    if (!isSwitchingScreens) {
                        jwtToken = it
                        navController?.navigate(SEARCHING_ONLINE_GAME_SCREEN)
                        // we make sure to only change screen once
                        isSwitchingScreens = true
                    }
                }
                Box {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.username), "your username"
                        )
                        TextField(username.value, { newValue ->
                            username.value = newValue
                            isUsernameValid.value = loginValidator(username.value)
                        }, label = {
                            Row {
                                if (!isUsernameValid.value) {
                                    Text(
                                        getString(R.string.invalid_login),
                                        modifier = Modifier,
                                        color = Color.Red,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }, placeholder = { Text(getString(R.string.username)) })
                    }
                }
                val isPasswordValid = remember { mutableStateOf(false) }
                val password = remember { mutableStateOf("") }
                Box {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.password), "your password"
                        )
                        TextField(password.value, { newValue ->
                            password.value = newValue
                            isPasswordValid.value = passwordValidator(password.value)
                        }, label = {
                            Row {
                                if (!isPasswordValid.value) {
                                    Text(
                                        getString(R.string.invalid_password),
                                        modifier = Modifier,
                                        color = Color.Red,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }, placeholder = { Text(getString(R.string.password)) })
                    }
                }
                Spacer(modifier = Modifier.height(100.dp))
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        requestInProcess.value = true
                        CoroutineScope(networkScope).launch {
                            serverResponse.value = Auth.login(username.value, password.value)
                            requestInProcess.value = false
                        }
                    },
                    enabled = isUsernameValid.value && isPasswordValid.value
                            && !requestInProcess.value
                ) {
                    Text(getString(R.string.sign_in))
                }
                Spacer(modifier = Modifier.height(100.dp))
                Box {
                    Button(modifier = Modifier, onClick = {
                        navController?.navigate(SIGN_UP_SCREEN)
                    }) {
                        Text(getString(R.string.no_account_sign_up))
                    }
                }
            }
        }
    }
}
