package com.github.io.mangjoo.realworld.user.service

import com.github.io.mangjoo.realworld.auth.config.JwtSupplier
import com.github.io.mangjoo.realworld.user.domain.User
import com.github.io.mangjoo.realworld.user.exception.UserException
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


interface UserRegist {
    fun regist(registUseCaseRequest: RegistUseCaseRequest): UserResponse

    @Service
    @Transactional
    class UserRegistUsecase(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val jwtSupplier: JwtSupplier
    ) : UserRegist {
        override fun regist(registUseCaseRequest: RegistUseCaseRequest): UserResponse =
            if (userRepository.checkDuplication(registUseCaseRequest.email)) {
                throw UserException("${registUseCaseRequest.email} is already exists")
            } else {
                val user =
                    userRepository.save(registUseCaseRequest.toDomain(passwordEncoder.encode(registUseCaseRequest.password)))
                UserResponse.from(user, jwtSupplier.supply(user))
            }
    }

    data class RegistUseCaseRequest(
        val email: String,
        val password: String,
        val username: String
    ) {
        fun toDomain(encodePassword: String): User =
            User(
                email = email,
                password = encodePassword,
                username = username
            )
    }

    data class UserResponse(
        val email: String,
        val token: String,
        val username: String,
        val bio: String,
        val image: String,
    ) {
        companion object {
            fun from(user: User, token: String): UserResponse =
                UserResponse(
                    email = user.email,
                    token = token,
                    username = user.username,
                    bio = user.bio,
                    image = user.image,
                )
        }
    }
}