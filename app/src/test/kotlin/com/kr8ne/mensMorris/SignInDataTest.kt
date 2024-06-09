package com.kr8ne.mensMorris

import com.kr8ne.mensMorris.common.commonLoginNameValidator
import com.kr8ne.mensMorris.common.commonPasswordValidator
import com.kr8ne.mensMorris.data.local.impl.auth.SignInData
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import java.util.concurrent.TimeUnit

class SignInDataTest {
    @Test
    @Timeout(3, unit = TimeUnit.SECONDS)
    fun loginNameValidator() {
        commonLoginNameValidator(SignInData())
    }

    @Test
    @Timeout(3, unit = TimeUnit.SECONDS)
    fun passwordValidator() {
        commonPasswordValidator(SignInData())
    }
}
