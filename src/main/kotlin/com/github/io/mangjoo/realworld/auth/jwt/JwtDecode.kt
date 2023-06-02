package com.github.io.mangjoo.realworld.auth.jwt

import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.stereotype.Component

@Component
class JwtDecode(
    private val jwtDecoder: JwtDecoder
) {
    fun tokenToUserId(token: String?): Long? = when(token) {
        null -> null
        else -> jwtDecoder.decode(token.removePrefix("Bearer ")).subject.toLong()
    }
}