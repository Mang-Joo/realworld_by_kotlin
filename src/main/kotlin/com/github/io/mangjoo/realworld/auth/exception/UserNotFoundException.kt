package com.github.io.mangjoo.realworld.auth.exception

import org.springframework.security.core.AuthenticationException

class UserNotFoundException(
    msg: String = "User not found"
) : AuthenticationException(msg)