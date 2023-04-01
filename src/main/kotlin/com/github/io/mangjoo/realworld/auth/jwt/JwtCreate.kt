package com.github.io.mangjoo.realworld.auth.jwt

import io.jsonwebtoken.Header.*
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm.*
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets.*
import java.security.Key
import java.util.Date
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component


@Component
class JwtCreate(
    @Value("\${jwt.token.secretKey}")
    private val secret: String,
    @Value("\${jwt.token.time}")
    private val time: Long,
) {
    private val key: Key = Keys.hmacShaKeyFor(secret.toByteArray(UTF_8))
    private val now = Date()

    fun createToken(userId: Long, role: GrantedAuthority): String =
        Jwts.builder()
            .setHeaderParam(TYPE, JWT_TYPE)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + time))
            .setId(userId.toString())
            .setClaims(body(userId.toString(), role.authority))
            .signWith(key, HS256)
            .compact()

    private fun body(userId: String, role: String) = Jwts.claims()
        .also { it.subject = userId }
        .also { it["role"] = role }
}