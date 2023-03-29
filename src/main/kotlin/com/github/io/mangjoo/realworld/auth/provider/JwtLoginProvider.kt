package com.github.io.mangjoo.realworld.auth.provider

import com.github.io.mangjoo.realworld.auth.exception.PasswordNotMatchException
import com.github.io.mangjoo.realworld.auth.service.JwtUserDetailsService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class JwtLoginProvider(
    private val userDetailsService: JwtUserDetailsService,
    private val passwordEncoder: PasswordEncoder
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication = authentication
        .let { it.principal as String }
        .let { userDetailsService.loadUserByUsername(it) }
        .apply {
            passwordEncoder.matches(authentication.credentials as String, password)
                .takeIf { it }
                ?: throw PasswordNotMatchException("Password not match")
        }
        .let { UsernamePasswordAuthenticationToken(it.id, null, listOf(SimpleGrantedAuthority(it.role.name))) }

    override fun supports(authentication: Class<*>): Boolean =
        authentication.isAssignableFrom(JwtLoginProvider::class.java)

}