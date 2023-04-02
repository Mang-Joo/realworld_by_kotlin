package com.github.io.mangjoo.realworld.auth.config

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey.Builder
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.SecurityContext
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder

@Configuration
class JwtConfig {
    @Bean
    fun jwtEncoder(
        @Value("\${jwt.token.private}") privateKey: RSAPrivateKey,
        @Value("\${jwt.token.public}") publicKey: RSAPublicKey,
    ): JwtEncoder = Builder(publicKey).privateKey(privateKey).build()
        .let { ImmutableJWKSet<SecurityContext>(JWKSet(it)) }
        .let { NimbusJwtEncoder(it) }

    @Bean
    fun jwtDecoder(
        @Value("\${jwt.token.public}") publicKey: RSAPublicKey,
    ): JwtDecoder = NimbusJwtDecoder.withPublicKey(publicKey).build()
}