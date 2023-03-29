package com.github.io.mangjoo.realworld.auth.filter

import com.fasterxml.jackson.databind.DatabindException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher


class JwtLoginFilter(
    requestMatcher: RequestMatcher,
) : AbstractAuthenticationProcessingFilter(requestMatcher) {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication = request
        .let { it ?: throw IllegalArgumentException("Request must not be null") }
        .apply { if (contentType != "application/json") throw IllegalArgumentException("Content-Type must be application/json") }
        .let {
            createLoginUserRequest(it)
        }
        .let { UsernamePasswordAuthenticationToken(it.username, it.password) }

    private fun createLoginUserRequest(it: HttpServletRequest): LoginUserRequest =
        try {
            jacksonObjectMapper()
                .readerFor(LoginUserRequest::class.java)
                .readValue(it.reader)
        } catch (e: DatabindException) {
            throw IllegalArgumentException("LoginUserRequest must not be null")
        }
}


class LoginUserRequest(
    val username: String,
    val password: String
)