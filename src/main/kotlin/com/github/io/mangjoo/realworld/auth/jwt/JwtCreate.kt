package com.github.io.mangjoo.realworld.auth.jwt

import io.jsonwebtoken.Header.*
import io.jsonwebtoken.SignatureAlgorithm.*
import java.nio.charset.StandardCharsets.*
import java.time.Instant
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Component


@Component
class JwtCreate(
    private val jwtEncoder: JwtEncoder,
    @Value("\${jwt.token.time}")
    private val time: Long,
) {
    private val now = Instant.now()

    fun createToken(userId: Long, role: GrantedAuthority): String = JwtClaimsSet.builder()
        .issuer("realworld")
        .issuedAt(Instant.now())
        .expiresAt(now.plusSeconds(time))
        .subject(userId.toString())
        .claims { it["role"] = role.authority }
        .build()
        .let { JwtEncoderParameters.from(it) }
        .let { jwtEncoder.encode(it).tokenValue }
}