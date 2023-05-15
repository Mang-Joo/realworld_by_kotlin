package com.github.io.mangjoo.realworld.auth.service

import com.github.io.mangjoo.realworld.user.repository.UserEntity
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    private val userRepository: UserRepository
) {
    fun loadUserByUsername(email: String): UserEntity =
        userRepository.findByEmail(email)
}