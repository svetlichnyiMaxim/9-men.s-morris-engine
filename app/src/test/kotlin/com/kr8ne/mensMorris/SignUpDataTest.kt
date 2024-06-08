package com.kr8ne.mensMorris

import com.kr8ne.mensMorris.common.commonLoginNameValidator
import com.kr8ne.mensMorris.common.commonPasswordValidator
import com.kr8ne.mensMorris.data.local.impl.auth.SignUpData
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import java.util.concurrent.TimeUnit

class SignUpDataTest {
    @Test
    @Timeout(3, unit = TimeUnit.SECONDS)
    fun loginNameValidator() {
        commonLoginNameValidator(SignUpData())
    }

    @Test
    @Timeout(3, unit = TimeUnit.SECONDS)
    fun passwordValidator() {
        commonPasswordValidator(SignUpData())
    }
}