package com.github.io.mangjoo.realworld.auth.exception

import org.springframework.security.core.AuthenticationException

class JwtNullException(
    override val message: String?
) : AuthenticationException(message)