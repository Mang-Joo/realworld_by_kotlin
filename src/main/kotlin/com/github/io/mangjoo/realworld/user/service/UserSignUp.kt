package com.github.io.mangjoo.realworld.user.service

import com.github.io.mangjoo.realworld.auth.config.JwtSupplier
import com.github.io.mangjoo.realworld.user.domain.User
import com.github.io.mangjoo.realworld.user.domain.vo.UserInfo
import com.github.io.mangjoo.realworld.user.exception.UserException
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


interface UserSignUp {
    fun signUp(signUpUseCaseRequest: SignUpUseCaseRequest): UserInfo

    @Service
    @Transactional
    class UserSignUpUseCase(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val jwtSupplier: JwtSupplier
    ) : UserSignUp {
        override fun signUp(signUpUseCaseRequest: SignUpUseCaseRequest): UserInfo =
            if (userRepository.checkDuplication(signUpUseCaseRequest.email)) {
                throw UserException("${signUpUseCaseRequest.email} is already exists")
            } else {
                val user = userRepository
                    .save(signUpUseCaseRequest.toDomain(passwordEncoder.encode(signUpUseCaseRequest.password)))
                user.userInfo
                    .apply { token = jwtSupplier.supply(user) }
            }
    }

    data class SignUpUseCaseRequest(
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
}