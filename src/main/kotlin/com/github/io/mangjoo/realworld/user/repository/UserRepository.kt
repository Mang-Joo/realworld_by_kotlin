package com.github.io.mangjoo.realworld.user.repository

import com.github.io.mangjoo.realworld.auth.exception.UserNotFoundException
import com.github.io.mangjoo.realworld.user.domain.User
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

interface UserRepository {
    fun findByEmail(email: String): User

    fun save(user: User): User

    fun checkDuplication(email: String): Boolean

    @Component
    @Transactional
    class UserRepositoryImpl(
        private val userJpaRepository: UserJpaRepository
    ) : UserRepository {
        override fun findByEmail(email: String): User =
            userJpaRepository.findByEmail(email)
                ?: throw UserNotFoundException("$email not found")

        override fun save(user: User): User = userJpaRepository.save(user)
        override fun checkDuplication(email: String): Boolean  = try {
            findByEmail(email)
            true
        } catch (e: UserNotFoundException) {
            false
        }
    }
}