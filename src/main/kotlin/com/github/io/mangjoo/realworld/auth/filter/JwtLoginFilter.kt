package com.github.io.mangjoo.realworld.auth.filter

import com.fasterxml.jackson.databind.DatabindException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.io.mangjoo.realworld.auth.exception.UserNotFoundException
import com.github.io.mangjoo.realworld.auth.handler.FailureHandler
import com.github.io.mangjoo.realworld.auth.handler.SuccessHandler
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher


class JwtLoginFilter(
    requestMatcher: RequestMatcher,
    private val successHandler: SuccessHandler,
    private val failureHandler: FailureHandler
) : AbstractAuthenticationProcessingFilter(requestMatcher) {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication = request
        .let { it ?: throw IllegalArgumentException("Request must not be null") }
        .apply { if (contentType != "application/json") throw IllegalArgumentException("Content-Type must be application/json") }
        .let { createLoginUserRequest(it) }
        .let { authenticationManager.authenticate(UsernamePasswordAuthenticationToken(it.username, it.password, null)) }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {
        SecurityContextHolder
            .createEmptyContext()
            .authentication = authResult

        successHandler.onAuthenticationSuccess(request, response, authResult)
    }

    override fun unsuccessfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, failed: AuthenticationException?) {
        failureHandler.onAuthenticationFailure(request, response, failed)
    }

    private fun createLoginUserRequest(it: HttpServletRequest): LoginUserRequest =
        try {
            jacksonObjectMapper()
                .readerFor(LoginUserRequest::class.java)
                .readValue(it.reader)
        } catch (e: DatabindException) {
            throw UserNotFoundException("Please enter normal information.")
        }
}


data class LoginUserRequest(
    val username: String,
    val password: String
)