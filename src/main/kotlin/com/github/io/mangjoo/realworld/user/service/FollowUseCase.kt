package com.github.io.mangjoo.realworld.user.service

import com.github.io.mangjoo.realworld.profile.vo.Profile
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

interface FollowUseCase {
    fun follow(to: String, from: Long): Profile

    @Service
    @Transactional
    class FollowUseCaseImpl(
        private val userRepository: UserRepository
    ) : FollowUseCase {
        override fun follow(to: String, from: Long): Profile {
            val following = userRepository.findByName(to)
            return userRepository.findById(from)
                .follow(following)
                .let { Profile(username = following.username, bio = following.bio, image = following.image, following = true) }
        }
    }
}