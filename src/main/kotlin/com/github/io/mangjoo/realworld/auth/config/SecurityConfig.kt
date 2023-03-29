package com.github.io.mangjoo.realworld.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {

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
        .build()


}