package com.github.io.mangjoo.realworld.user.api

import com.github.io.mangjoo.realworld.user.service.UserRegist
import com.github.io.mangjoo.realworld.user.service.UserRegist.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RegistController(
    private val userRegist: UserRegist
) {

    @PostMapping("/api/user")
    fun regist(@RequestBody registRequest: RegistRequest) =
        userRegist.regist(registRequest.toUseCaseRequest())
            .let { ResponseEntity.ok(it) }

    class RegistRequest(
        private val email: String,
        private val password: String,
        private val username: String
    ) {
        fun toUseCaseRequest(): RegistUseCaseRequest =
            RegistUseCaseRequest(
                email = email,
                password = password,
                username = username
            )
    }


}