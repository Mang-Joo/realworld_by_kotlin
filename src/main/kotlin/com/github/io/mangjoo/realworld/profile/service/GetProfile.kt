package com.github.io.mangjoo.realworld.profile.service

import com.github.io.mangjoo.realworld.profile.vo.Profile
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.stereotype.Service

interface GetProfile {

    fun getProfile(username: String, followerId: Long?): Profile

    @Service
    class GetProfileUseCase(
        private val userRepository: UserRepository
    ) : GetProfile {
        override fun getProfile(username: String, followerId: Long?): Profile = if (followerId == null) {
            userRepository.findByName(username)
                .let {
                    Profile(
                        it.userInfo.username,
                        it.userInfo.bio,
                        it.userInfo.image
                    )
                }
        } else {
            userRepository.findByName(username).let {
                Profile(
                    it.username,
                    it.bio,
                    it.image,
                    it.isFollowing(followerId)
                )
            }

        }
    }
}