package com.github.io.mangjoo.realworld.user.service

import com.github.io.mangjoo.realworld.user.domain.vo.UserInfo
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.stereotype.Service

interface GetUserInfo {
    fun findUser(id: Long): UserInfo

    @Service
    class GetUserInfoUseCaseRequest(
        private val userRepository: UserRepository
    ): GetUserInfo{
        override fun findUser(id: Long) =
            userRepository.findById(id).userInfo
    }
}