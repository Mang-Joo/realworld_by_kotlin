package com.github.io.mangjoo.realworld.auth.config

import com.github.io.mangjoo.realworld.auth.filter.JwtLoginFilter
import com.github.io.mangjoo.realworld.auth.provider.JwtLoginProvider
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey.*
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.SecurityContext
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.POST
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtLoginProvider: JwtLoginProvider
) {

    @Bean
    fun configure(
        httpSecurity: HttpSecurity,
        authenticationManager: AuthenticationManager,
    ): SecurityFilterChain = httpSecurity
        .csrf().disable()
        .sessionManagement()
        .sessionCreationPolicy(STATELESS)
        .and()
        .authorizeHttpRequests()
        .requestMatchers("/api/v1/login").permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .oauth2ResourceServer { it.jwt() }
        .addJwtLoginFilter(authenticationManager)
        .build()

    private fun HttpSecurity.addJwtLoginFilter(authenticationManager: AuthenticationManager): HttpSecurity = addFilterBefore(
        JwtLoginFilter(AntPathRequestMatcher("/api/v1/login", POST.name()))
            .apply { setAuthenticationManager(authenticationManager) },
        UsernamePasswordAuthenticationFilter::class.java
    )

    @Bean
    fun authenticationManager(): AuthenticationManager = ProviderManager(listOf(jwtLoginProvider))

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