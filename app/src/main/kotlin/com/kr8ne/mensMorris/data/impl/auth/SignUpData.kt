package com.kr8ne.mensMorris.data.impl.auth

import com.kr8ne.mensMorris.api.Client
import com.kr8ne.mensMorris.data.interfaces.DataModel

/**
 * This class provides data for sign in screen.
 */
class SignUpData : DataModel {
    /**
     * Validates the provided login.
     *
     * @param login The login to be validated.
     * @return True if the login is valid, false otherwise.
     */
    fun loginValidator(login: String): Boolean {
        return Client.loginValidator(login)
    }

    /**
     * Validates the provided password.
     *
     * @param password The password to be validated.
     * @return True if the password is valid, false otherwise.
     */
    fun passwordValidator(password: String): Boolean {
        return Client.passwordValidator(password)
    }
}
