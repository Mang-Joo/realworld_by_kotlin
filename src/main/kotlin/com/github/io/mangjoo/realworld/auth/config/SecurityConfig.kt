package com.github.io.mangjoo.realworld.auth.config

import com.github.io.mangjoo.realworld.auth.filter.JwtLoginFilter
import com.github.io.mangjoo.realworld.auth.provider.JwtLoginProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.POST
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
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
        .addJwtLoginFilter(authenticationManager)
        .build()

    private fun HttpSecurity.addJwtLoginFilter(authenticationManager: AuthenticationManager): HttpSecurity = addFilterBefore(
        JwtLoginFilter(AntPathRequestMatcher("/api/v1/login", POST.name()))
            .apply { setAuthenticationManager(authenticationManager) },
        UsernamePasswordAuthenticationFilter::class.java
    )

    @Bean
    fun authenticationManager(): AuthenticationManager = ProviderManager(listOf(jwtLoginProvider))
}