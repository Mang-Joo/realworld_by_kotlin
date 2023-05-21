package com.github.io.mangjoo.realworld.auth.config

import com.github.io.mangjoo.realworld.user.domain.User
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class JwtSupplier(
    private val jwtEncoder: JwtEncoder
) {
    fun supply(user: User): String = Instant.now()
        .let {
            JwtClaimsSet.builder()
                .issuer("https://realworld.io")
                .issuedAt(it)
                .expiresAt(it.plusSeconds(300))
                .subject(user.id.toString())
                .claim("email", user.email)
                .build();
        }
        .let { JwtEncoderParameters.from(it) }
        .let { jwtEncoder.encode(it) }.tokenValue
}