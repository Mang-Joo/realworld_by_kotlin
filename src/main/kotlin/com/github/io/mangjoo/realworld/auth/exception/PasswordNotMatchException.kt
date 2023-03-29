package com.github.io.mangjoo.realworld.auth.exception

import org.springframework.security.core.AuthenticationException

class PasswordNotMatchException(
    msg: String = "Password not match"
): AuthenticationException(msg)