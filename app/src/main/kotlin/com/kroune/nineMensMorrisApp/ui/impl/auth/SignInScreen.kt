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
import com.kroune.nineMensMorrisApp.viewModel.impl.auth.SignInViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Represents a screen for signing in to the application.
 */
class SignInScreen(
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

    override lateinit var viewModel: SignInViewModel

    @Composable
    override fun InvokeRender() {
        viewModel = hiltViewModel()
        val serverResponse = remember { mutableStateOf<Result<String>?>(null) }
        val requestInProcess = remember { mutableStateOf(false) }
        val isUsernameValid = remember { mutableStateOf(false) }
        val username = remember { mutableStateOf("") }
        serverResponse.value?.onSuccess {
            viewModel.updateJwtToken(it)
            if (nextRoute is Navigation.ViewAccount) {
                CoroutineScope(networkScope).launch {
                    val id = viewModel.getIdByJwtToken(it)
                    if (id.isFailure) {
                        // this means we got some error, so we can tell user about it
                        val exception = id.exceptionOrNull()!!
                        serverResponse.value = Result.failure(exception)
                        return@launch
                    }
                    if (id.getOrThrow() == null) {
                        // this means some server error occurred
                        serverResponse.value =
                            Result.failure(IllegalStateException("server didn't return id for account"))
                        return@launch
                    }
                    withContext(Dispatchers.Main) {
                        navController?.navigateSingleTopTo(Navigation.ViewAccount(id.getOrThrow()!!))
                    }
                }
            } else {
                navController?.navigateSingleTopTo(nextRoute)
            }
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
                Spacer(modifier = Modifier.fillMaxHeight(0.3f))
                TextField(
                    value = username.value,
                    onValueChange = { newValue ->
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
                    placeholder = {
                        Text(resources.getString(R.string.username))
                    },
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.username), null)
                    }
                )
                Spacer(modifier = Modifier.fillMaxHeight(0.05f))
                val isPasswordValid = remember { mutableStateOf(false) }
                val password = remember { mutableStateOf("") }
                TextField(
                    password.value,
                    { newValue ->
                        password.value = newValue
                        isPasswordValid.value = viewModel.passwordValidator(password.value)
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
                            painter = painterResource(id = R.drawable.password), null
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
                                viewModel.login(username.value, password.value)
                            requestInProcess.value = false
                        }
                    },
                    enabled = isUsernameValid.value && isPasswordValid.value && !requestInProcess.value
                ) {
                    Text(resources.getString(R.string.sign_in))
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(resources.getString(R.string.no_account_question))
                        TextButton(modifier = Modifier, onClick = {
                            navController?.navigateSingleTopTo(Navigation.SignUp(nextRoute))
                        }) {
                            Text(resources.getString(R.string.sign_up), color = Color.Blue)
                        }
                    }
                }
                Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            }
        }
    }
}
