package com.kr8ne.mensMorris.common

import com.kr8ne.mensMorris.data.local.impl.auth.SignInData
import com.kr8ne.mensMorris.data.local.impl.auth.SignUpData

fun commonLoginNameValidator(parent: Any) {

    fun Any.loginValidator(s: String): Boolean {
        if (this is SignUpData) {
            return this.loginValidator(s)
        }
        if (this is SignInData) {
            return this.loginValidator(s)
        }
        error("tricky cast failed")
    }
    // invalid
    run {
        // length
        assert(!parent.loginValidator(""))
        assert(!parent.loginValidator("a"))
        assert(!parent.loginValidator("sn"))
        assert(!parent.loginValidator("sn3"))
        assert(!parent.loginValidator("3034"))
        // illegal symbols
        assert(!parent.loginValidator("@@@q@@@"))
        assert(!parent.loginValidator("anc!!!!!"))
        assert(!parent.loginValidator("name withSpace"))
        // too long
        assert(!parent.loginValidator("veryVeryVeryLongName"))
    }
    // valid
    run {
        assert(parent.loginValidator("someLongName"))
        assert(parent.loginValidator("anotherValid"))
        assert(parent.loginValidator("corRectName"))
        assert(parent.loginValidator("120player"))
        assert(parent.loginValidator("hhahaha0"))
    }
}


fun commonPasswordValidator(parent: Any) {

    fun Any.passwordValidator(s: String): Boolean {
        if (this is SignUpData) {
            return this.passwordValidator(s)
        }
        if (this is SignInData) {
            return this.passwordValidator(s)
        }
        error("tricky cast failed")
    }
    assert(!parent.passwordValidator(""))
    assert(!parent.passwordValidator("a"))
    assert(!parent.passwordValidator("sn"))
    assert(!parent.passwordValidator("sn3"))
    assert(!parent.passwordValidator("3034"))
    // illegal symbols
    assert(!parent.passwordValidator("@@@q@@@"))
    assert(!parent.passwordValidator("anc!!!!!"))
    assert(!parent.passwordValidator("name withSpace"))
    // too long
    assert(!parent.passwordValidator("veryVeryVeryLongName"))
    // no numbers
    assert(!parent.passwordValidator("anotherValid"))
    assert(!parent.passwordValidator("corRectName"))
    // valid
    run {
        assert(parent.passwordValidator("120player"))
        assert(parent.passwordValidator("hhahaha0"))
        assert(parent.passwordValidator("somePassword1"))
    }
}
