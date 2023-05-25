package com.github.io.mangjoo.realworld.fixture

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.io.mangjoo.realworld.user.api.SignUpController.SignUpRequest
import com.github.io.mangjoo.realworld.user.api.UserInfoResponse
import com.github.io.mangjoo.realworld.user.domain.User
import com.github.io.mangjoo.realworld.user.domain.vo.Role.ROLE_USER
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

class Fixture {

    val user = User("mangjoo@gmail.com", "password", "mangjoo", "bio", "image", true, ROLE_USER)
        .apply {
            id = 1
        }

    val follower = User("follower@gmail.com", "password", "follower")
        .apply {
            id = 2
            this.follow(user)
        }

    val follower2 = User("followe22r@gmail.com", "password22", "follower22")
        .apply { id = 3 }

    val userToken: String =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlJPTEVfVVNFUiJ9.n_Voazt4-FHnoVxrvnCSIDCepqK8cd-vEQYZLbsZ2Hc"

}

fun MockMvc.signUpUser(
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