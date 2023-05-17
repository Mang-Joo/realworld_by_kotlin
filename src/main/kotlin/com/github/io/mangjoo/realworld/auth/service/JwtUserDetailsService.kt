package com.github.io.mangjoo.realworld.auth.service

import com.github.io.mangjoo.realworld.user.domain.User
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    private val userRepository: UserRepository
) {
    fun loadUserByUsername(email: String): User =
        userRepository.findByEmail(email)
}