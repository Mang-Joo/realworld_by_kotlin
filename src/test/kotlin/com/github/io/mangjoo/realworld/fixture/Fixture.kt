package com.github.io.mangjoo.realworld.fixture

import com.github.io.mangjoo.realworld.auth.domain.Role.ROLE_USER
import com.github.io.mangjoo.realworld.auth.domain.UserEntity

class Fixture {
    val userEntity = UserEntity(1L, "mangjoo@gmail.com", "password", "mangjoo", "bio", "image", true, ROLE_USER)
    val userToken: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlJPTEVfVVNFUiJ9.n_Voazt4-FHnoVxrvnCSIDCepqK8cd-vEQYZLbsZ2Hc"
}