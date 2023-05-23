package com.github.io.mangjoo.realworld.user.service

import com.github.io.mangjoo.realworld.user.domain.vo.UserInfo
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

interface UpdateUser {
    fun updateUser(userInfo: UserInfo, updatePassword: String): UserInfo

    @Service
    class UpdateUserUseCase(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
    ) : UpdateUser {
        override fun updateUser(userInfo: UserInfo, updatePassword: String): UserInfo =
            userRepository.findByEmail(userInfo.email)
                .let {
                    it.updateUserInfo(userInfo)
                        .updatePassword(passwordEncoder.encode(updatePassword))
                }.userInfo
    }
}