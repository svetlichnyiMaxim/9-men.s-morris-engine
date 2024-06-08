package com.kr8ne.mensMorris.data.local.impl.auth

import com.kr8ne.mensMorris.data.local.interfaces.DataI
import com.kr8ne.mensMorris.data.remote.Auth

/**
 * This class provides data for sign in screen.
 */
class SignInData : DataI() {
    /**
     * Validates the provided login.
     *
     * @param login The login to be validated.
     * @return True if the login is valid, false otherwise.
     */
    fun loginValidator(login: String): Boolean {
        return Auth.loginValidator(login)
    }

    /**
     * Validates the provided password.
     *
     * @param password The password to be validated.
     * @return True if the password is valid, false otherwise.
     */
    fun passwordValidator(password: String): Boolean {
        return Auth.passwordValidator(password)
    }
}
