package com.github.io.mangjoo.realworld.fixture

import com.github.io.mangjoo.realworld.auth.domain.Role.ROLE_USER
import com.github.io.mangjoo.realworld.auth.domain.UserEntity

class Fixture {
    val userEntity = UserEntity(1L, "mangjoo@gmail.com", "password", "mangjoo", "bio", "image", true, ROLE_USER)
}