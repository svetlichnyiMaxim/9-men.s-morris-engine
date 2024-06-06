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
import com.kr8ne.mensMorris.SIGN_IN_SCREEN
import com.kr8ne.mensMorris.api.Client
import com.kr8ne.mensMorris.api.ServerResponse
import com.kr8ne.mensMorris.common.AppTheme
import com.kr8ne.mensMorris.getString
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.SocketException

/**
 * Represents a screen for signing up a new user.
 *
 * @param navController The navigation controller to navigate to other screens.
 * @param loginValidator A function that validates the provided username or email.
 * @param passwordValidator A function that validates the provided password.
 */
class SignUpScreen(
    /**
     * navigation controller
     */
    val navController: NavHostController?,
    /**
     * A function that validates the provided username or email.
     */
    val loginValidator: (String) -> Boolean,
    /**
     * A function that validates the provided password.
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
                val usernameOrEmail = remember { mutableStateOf("") }
                serverResponse.value?.onFailure { exception ->
                    when (exception) {
                        is ServerResponse.LoginInUse -> {
                            Text(text = getString(R.string.login_in_use))
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
                        Client.jwtToken = it
                        navController?.navigate(SEARCHING_ONLINE_GAME_SCREEN)
                        // we make sure to only change screen once
                        isSwitchingScreens = true
                    }
                }
                Box {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.username),
                            "your preferred username"
                        )
                        TextField(usernameOrEmail.value, { newValue ->
                            usernameOrEmail.value = newValue
                            Client.updateUserLogin(newValue)
                            isUsernameValid.value = loginValidator(usernameOrEmail.value)
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
                val isPassword2Valid = remember { mutableStateOf(false) }
                val password2 = remember { mutableStateOf("") }
                Box {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.password), "your new password"
                        )
                        TextField(password.value, { newValue ->
                            password.value = newValue
                            Client.updateUserPassword(newValue)
                            isPasswordValid.value = passwordValidator(password.value)
                            isPassword2Valid.value = (password2.value == password.value)
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
                Box {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.password),
                            getString(R.string.repeat_pass)
                        )
                        TextField(password2.value, { newValue ->
                            password2.value = newValue
                            isPassword2Valid.value = (password2.value == password.value)
                        }, label = {
                            Row {
                                if (!isPassword2Valid.value) {
                                    Text(
                                        getString(R.string.pass_doesnt_match),
                                        modifier = Modifier,
                                        color = Color.Red,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }, placeholder = { Text(getString(R.string.repeat_pass)) })
                    }
                }
                Spacer(modifier = Modifier.height(100.dp))
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        requestInProcess.value = true
                        CoroutineScope(Client.networkScope).launch {
                            serverResponse.value = Client.register()
                            requestInProcess.value = false
                        }
                    },
                    enabled = isUsernameValid.value && isPasswordValid.value &&
                            isPassword2Valid.value && !requestInProcess.value
                ) {
                    Text(getString(R.string.sign_up))
                }
                Spacer(modifier = Modifier.height(100.dp))
                Box {
                    Button(modifier = Modifier, onClick = {
                        navController?.navigate(SIGN_IN_SCREEN)
                    }) {
                        Text(getString(R.string.have_account_sign_in))
                    }
                }
            }
        }
    }
}
