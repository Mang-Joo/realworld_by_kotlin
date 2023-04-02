package com.github.io.mangjoo.realworld.auth.repository

import com.github.io.mangjoo.realworld.auth.domain.UserEntity
import com.github.io.mangjoo.realworld.auth.exception.UserNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

interface UserRepository {
    fun findByEmail(email: String): UserEntity

    fun save(userEntity: UserEntity): UserEntity

    @Component
    @Transactional
    class UserRepositoryImpl(
        private val userJpaRepository: UserJpaRepository
    ) : UserRepository {
        override fun findByEmail(email: String): UserEntity =
            userJpaRepository.findByEmail(email)
                ?: throw UserNotFoundException("$email not found")

        override fun save(userEntity: UserEntity): UserEntity = userJpaRepository.save(userEntity)
    }
}