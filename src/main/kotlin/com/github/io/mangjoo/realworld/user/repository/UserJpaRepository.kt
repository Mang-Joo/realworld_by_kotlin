package com.github.io.mangjoo.realworld.user.repository

import com.github.io.mangjoo.realworld.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository: JpaRepository<User, Long> {
    fun findByUserInfo_Email(email: String): User?

    fun findByUserInfo_Username(username: String): User?
}