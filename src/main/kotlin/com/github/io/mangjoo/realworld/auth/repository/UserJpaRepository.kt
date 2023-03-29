package com.github.io.mangjoo.realworld.auth.repository

import com.github.io.mangjoo.realworld.auth.domain.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository: JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}