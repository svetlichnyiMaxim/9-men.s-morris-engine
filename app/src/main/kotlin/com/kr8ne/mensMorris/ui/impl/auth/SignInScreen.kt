package com.kr8ne.mensMorris.ui.impl.auth

import android.content.res.Resources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kr8ne.mensMorris.BUTTON_WIDTH
import com.kr8ne.mensMorris.R
import com.kr8ne.mensMorris.SEARCHING_ONLINE_GAME_SCREEN
import com.kr8ne.mensMorris.SIGN_UP_SCREEN
import com.kr8ne.mensMorris.common.AppTheme
import com.kr8ne.mensMorris.data.remote.Auth
import com.kr8ne.mensMorris.data.remote.Auth.jwtToken
import com.kr8ne.mensMorris.data.remote.networkScope
import com.kr8ne.mensMorris.ui.interfaces.ScreenModel
import com.kr8ne.mensMorris.viewModel.impl.auth.SignInViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Represents a screen for signing in to the application.
 *
 * @param navController The navigation controller for navigating between screens.
 */
class SignInScreen(
    /**
     * navigation controller
     */
    val navController: NavHostController?, val resources: Resources
) : ScreenModel {
    @Composable
    override fun InvokeRender() {
        val serverResponse = remember { mutableStateOf<Result<String>?>(null) }
        var isSwitchingScreens = remember { false }
        serverResponse.value?.onSuccess {
            if (!isSwitchingScreens) {
                jwtToken = it
                navController?.navigate(SEARCHING_ONLINE_GAME_SCREEN)
                // we make sure to only change screen once
                isSwitchingScreens = true
            }
        }
        AppTheme {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val requestInProcess = remember { mutableStateOf(false) }
                val isUsernameValid = remember { mutableStateOf(false) }
                val username = remember { mutableStateOf("") }
                serverResponse.value?.onFailure { exception ->
                    // TODO: finish this
                    Text(text = resources.getString(R.string.server_error))
                }
                Spacer(modifier = Modifier.fillMaxHeight(0.3f))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.username), "your username"
                    )
                    TextField(username.value, { newValue ->
                        username.value = newValue
                        isUsernameValid.value = viewModel.loginValidator(username.value)
                    }, label = {
                        Row {
                            if (!isUsernameValid.value) {
                                Text(
                                    resources.getString(R.string.invalid_login),
                                    modifier = Modifier,
                                    color = Color.Red,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }, placeholder = { Text(resources.getString(R.string.username)) })
                }
                Spacer(modifier = Modifier.fillMaxHeight(0.05f))
                val isPasswordValid = remember { mutableStateOf(false) }
                val password = remember { mutableStateOf("") }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.password), "your password"
                    )
                    TextField(password.value, { newValue ->
                        password.value = newValue
                        isPasswordValid.value = viewModel.passwordValidator(password.value)
                    }, label = {
                        Row {
                            if (!isPasswordValid.value) {
                                Text(
                                    resources.getString(R.string.invalid_password),
                                    modifier = Modifier,
                                    color = Color.Red,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }, placeholder = { Text(resources.getString(R.string.password)) })
                }
                Spacer(modifier = Modifier.fillMaxHeight(0.1f))
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        requestInProcess.value = true
                        CoroutineScope(networkScope).launch {
                            serverResponse.value = Auth.login(username.value, password.value)
                            requestInProcess.value = false
                        }
                    },
                    enabled = isUsernameValid.value && isPasswordValid.value && !requestInProcess.value
                ) {
                    Text(resources.getString(R.string.sign_in))
                }
                Box(
                    modifier = Modifier
                        .padding(bottom = BUTTON_WIDTH)
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(resources.getString(R.string.no_account_question))
                        TextButton(modifier = Modifier, onClick = {
                            navController?.navigate(SIGN_UP_SCREEN)
                        }) {
                            resources.getColor(R.color.purple_700, null)
                            Text(resources.getString(R.string.sign_up), color = Color.Blue)
                        }
                    }
                }
            }
        }
    }

    override val viewModel = SignInViewModel()
}

@Preview(device = "spec:parent=pixel_5")
@Composable
fun PreviewSignInScreen() {
    SignInScreen(null, Resources.getSystem()).InvokeRender()
}

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun PreviewSignInScreen1() {
    SignInScreen(null, Resources.getSystem()).InvokeRender()
}