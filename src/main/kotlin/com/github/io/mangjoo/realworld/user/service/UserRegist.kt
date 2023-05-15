package com.github.io.mangjoo.realworld.user.service

import com.github.io.mangjoo.realworld.user.exception.UserDuplicationException
import com.github.io.mangjoo.realworld.user.repository.UserEntity
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


interface UserRegist {
    fun regist(registUseCaseRequest: RegistUseCaseRequest): Long

    @Service
    @Transactional
    class UserRegistUsecase(
        private val userRepository: UserRepository
    ): UserRegist {
        override fun regist(registUseCaseRequest: RegistUseCaseRequest): Long {
            if (userRepository.checkDuplication(registUseCaseRequest.email)) {
                throw UserDuplicationException("${registUseCaseRequest.email} is already exists")
            }
            return userRepository.save(registUseCaseRequest.toEntity())
        }
    }

    data class RegistUseCaseRequest(
        val email: String,
        val password: String,
        val username: String
    ) {
        fun toEntity(): UserEntity =
            UserEntity(
                email = email,
                password = password,
                username = username
            )
    }

}