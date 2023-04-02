package com.github.io.mangjoo.realworld.auth.handler

import com.github.io.mangjoo.realworld.auth.jwt.JwtCreate
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponse.SC_OK
import java.nio.charset.StandardCharsets.UTF_8
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

private val HttpServletResponse?.checkResponse: HttpServletResponse
    get() = this ?: error("Response must not be null")

private val HttpServletResponse?.setSuccessResponseType: HttpServletResponse
    get() = this.checkResponse
        .apply {
            contentType = APPLICATION_JSON_VALUE
            characterEncoding = UTF_8.name()
            status = SC_OK
        }

private val Authentication?.convertByPrcinpalLongId: Long
    get() = this?.let { it.principal as Long } ?: error("Authentication must not be null")

private val Authentication?.convertByRole: GrantedAuthority
    get() = this?.authorities?.first() ?: error("Authentication must not be null")

@Component
class SuccessHandler(
    private val jwtCreate: JwtCreate
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        val jwt = jwtCreate.createToken(authentication.convertByPrcinpalLongId, authentication.convertByRole)
        response.setSuccessResponseType
            .writer
            .write(jwt)
    }
}