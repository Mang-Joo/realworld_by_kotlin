package com.github.io.mangjoo.realworld.fixture

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.io.mangjoo.realworld.user.api.SignUpController.SignUpRequest
import com.github.io.mangjoo.realworld.user.api.UserInfoResponse
import com.github.io.mangjoo.realworld.user.domain.Follow
import com.github.io.mangjoo.realworld.user.domain.User
import com.github.io.mangjoo.realworld.user.domain.vo.Role.ROLE_USER
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

class Fixture {

    val follower = User("follower@gmail.com", "password", "follower")
        .apply { id = 2 }

    val user = User("mangjoo@gmail.com", "password", "mangjoo", "bio", "image", true, ROLE_USER)
        .apply {
            id = 1
            val follow = Follow(follower = follower, following = this, id = 1)
            followers.add(follow)
        }

    val userToken: String =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlJPTEVfVVNFUiJ9.n_Voazt4-FHnoVxrvnCSIDCepqK8cd-vEQYZLbsZ2Hc"

}

fun MockMvc.singUpUser(
    request: SignUpRequest = SignUpRequest("mangjoo@email.com", "password", "mangjoo"),
): UserInfoResponse {
    return post("/api/user") {
        contentType = MediaType.APPLICATION_JSON
        content = jacksonObjectMapper().writeValueAsString(request)
    }.andReturn()
        .response
        .contentAsString
        .let { jacksonObjectMapper().readValue(it, UserInfoResponse::class.java) }
}