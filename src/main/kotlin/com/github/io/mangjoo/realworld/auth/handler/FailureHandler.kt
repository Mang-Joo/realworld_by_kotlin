package com.github.io.mangjoo.realworld.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.nio.charset.StandardCharsets
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component


private val HttpServletResponse?.checkResponse: HttpServletResponse
    get() = this ?: error("Response must not be null")

private val HttpServletResponse?.setFailureResponseType: HttpServletResponse
    get() = this.checkResponse
        .apply {
            contentType = MediaType.APPLICATION_JSON_VALUE
            characterEncoding = StandardCharsets.UTF_8.name()
            status = HttpServletResponse.SC_UNAUTHORIZED
        }
@Component
class FailureHandler: AuthenticationFailureHandler {
    override fun onAuthenticationFailure(request: HttpServletRequest?, response: HttpServletResponse?, exception: AuthenticationException?) {
        response.setFailureResponseType
            .writer
            .write(exception?.message ?: "Authentication failed")
    }
}