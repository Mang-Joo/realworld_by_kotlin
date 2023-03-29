package com.github.io.mangjoo.realworld.auth.repository

import com.github.io.mangjoo.realworld.auth.domain.UserEntity
import com.github.io.mangjoo.realworld.auth.exception.UserNotFoundException
import org.springframework.stereotype.Component

interface UserRepository {
    fun findByEmail(email: String): UserEntity

    @Component
    class UserRepositoryImpl(
        private val userJpaRepository: UserJpaRepository
    ) : UserRepository {
        override fun findByEmail(email: String): UserEntity =
            userJpaRepository.findByEmail(email)
                ?: throw UserNotFoundException("$email not found")
    }
}