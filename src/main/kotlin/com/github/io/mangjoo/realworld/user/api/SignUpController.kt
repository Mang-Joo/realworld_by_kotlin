package com.github.io.mangjoo.realworld.user.api

import com.github.io.mangjoo.realworld.user.service.UserSignUp
import com.github.io.mangjoo.realworld.user.service.UserSignUp.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SignUpController(
    private val userSignUp: UserSignUp
) {

    @PostMapping("/api/user")
    fun signUp(@RequestBody signUpRequest: SignUpRequest) =
        userSignUp.signUp(signUpRequest.toUseCaseRequest())
            .let { ResponseEntity.ok(UserInfoResponse.from(it)) }

    data class SignUpRequest(
        val email: String,
        val password: String,
        val username: String
    ) {
        fun toUseCaseRequest(): SignUpUseCaseRequest =
            SignUpUseCaseRequest(
                email = email,
                password = password,
                username = username
            )
    }



}