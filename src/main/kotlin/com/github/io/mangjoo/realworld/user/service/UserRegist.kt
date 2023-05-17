package com.github.io.mangjoo.realworld.user.service

import com.github.io.mangjoo.realworld.user.domain.User
import com.github.io.mangjoo.realworld.user.exception.UserDuplicationException
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


interface UserRegist {
    fun regist(registUseCaseRequest: RegistUseCaseRequest): UserResponse

    @Service
    @Transactional
    class UserRegistUsecase(
        private val userRepository: UserRepository
    ) : UserRegist {
        override fun regist(registUseCaseRequest: RegistUseCaseRequest): UserResponse =
            if (userRepository.checkDuplication(registUseCaseRequest.email)) {
                throw UserDuplicationException("${registUseCaseRequest.email} is already exists")
            } else UserResponse.from(
                userRepository.save(registUseCaseRequest.toEntity())
            )

    }

    data class RegistUseCaseRequest(
        val email: String,
        val password: String,
        val username: String
    ) {
        fun toEntity(): User =
            User(
                email = email,
                password = password,
                username = username
            )
    }

    data class UserResponse(
        val id: Long,
        val email: String,
        val password: String,
        val username: String
    ) {
        companion object {
            fun from(user: User): UserResponse =
                UserResponse(
                    id = user.id,
                    email = user.email,
                    password = user.password,
                    username = user.username
                )
        }
    }
}