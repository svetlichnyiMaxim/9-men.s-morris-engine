package com.kroune.nineMensMorrisApp.ui.impl.auth

import android.content.res.Resources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kroune.nineMensMorrisApp.Navigation
import com.kroune.nineMensMorrisApp.R
import com.kroune.nineMensMorrisApp.common.AppTheme
import com.kroune.nineMensMorrisApp.data.remote.Common.networkScope
import com.kroune.nineMensMorrisApp.navigateSingleTopTo
import com.kroune.nineMensMorrisApp.ui.interfaces.ScreenModelI
import com.kroune.nineMensMorrisApp.viewModel.impl.auth.SignUpViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Represents a screen for signing up a new user.
 */
class SignUpScreen(
    /**
     * navigation controller
     */
    private val navController: NavHostController?,
    private val nextRoute: Navigation,
    /**
     * resources
     * used for translations
     */
    private val resources: Resources
) : ScreenModelI {

    override lateinit var viewModel: SignUpViewModel

    @Composable
    override fun InvokeRender() {
        viewModel = hiltViewModel()
        val serverResponse = remember { mutableStateOf<Result<String>?>(null) }
        val password = remember { mutableStateOf("") }
        val isPasswordValid = remember { mutableStateOf(false) }
        val password2 = remember { mutableStateOf("") }
        val isPassword2Valid = remember { mutableStateOf(false) }
        val requestInProcess = remember { mutableStateOf(false) }
        val isUsernameValid = remember { mutableStateOf(false) }
        val username = remember { mutableStateOf("") }
        serverResponse.value?.onSuccess {
            viewModel.updateJwtToken(it)
            navController?.navigateSingleTopTo(nextRoute)
        }
        AppTheme {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                serverResponse.value?.onFailure { exception ->
                    // TODO: finish this
                    Text(text = resources.getString(R.string.server_error))
                }
                Spacer(modifier = Modifier.fillMaxHeight(0.25f))
                TextField(
                    username.value,
                    { newValue ->
                        username.value = newValue
                        isUsernameValid.value = viewModel.loginValidator(username.value)
                    },
                    label = {
                        if (!isUsernameValid.value) {
                            Text(
                                resources.getString(R.string.invalid_login),
                                modifier = Modifier,
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    },
                    placeholder = { Text(resources.getString(R.string.username)) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.username),
                            "your preferred username"
                        )
                    }
                )
                Spacer(modifier = Modifier.fillMaxHeight(0.025f))
                TextField(
                    password.value,
                    { newValue ->
                        password.value = newValue
                        isPasswordValid.value = viewModel.passwordValidator(password.value)
                        isPassword2Valid.value = (password2.value == password.value)
                    },
                    label = {
                        if (!isPasswordValid.value) {
                            Text(
                                resources.getString(R.string.invalid_password),
                                modifier = Modifier,
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    },
                    placeholder = { Text(resources.getString(R.string.password)) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.password),
                            "your new password"
                        )
                    }
                )
                Spacer(modifier = Modifier.fillMaxHeight(0.025f))
                TextField(
                    password2.value,
                    { newValue ->
                        password2.value = newValue
                        isPassword2Valid.value = (password2.value == password.value)
                    },
                    label = {
                        if (!isPassword2Valid.value) {
                            Text(
                                resources.getString(R.string.pass_doesnt_match),
                                modifier = Modifier,
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    },
                    placeholder = { Text(resources.getString(R.string.repeat_pass)) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.password),
                            resources.getString(R.string.repeat_pass)
                        )
                    }
                )
                Spacer(modifier = Modifier.fillMaxHeight(0.1f))
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        requestInProcess.value = true
                        CoroutineScope(networkScope).launch {
                            serverResponse.value =
                                viewModel.register(username.value, password.value)
                            requestInProcess.value = false
                        }
                    },
                    enabled = isUsernameValid.value && isPasswordValid.value &&
                            isPassword2Valid.value && !requestInProcess.value
                ) {
                    Text(resources.getString(R.string.sign_up))
                }
                Spacer(modifier = Modifier.fillMaxHeight(0.1f))
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(resources.getString(R.string.have_account_question))
                        TextButton(modifier = Modifier, onClick = {
                            navController?.navigateSingleTopTo(Navigation.SignIn(nextRoute))
                        }) {
                            Text(resources.getString(R.string.sign_in))
                        }
                    }
                }
                Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            }
        }
    }
}
