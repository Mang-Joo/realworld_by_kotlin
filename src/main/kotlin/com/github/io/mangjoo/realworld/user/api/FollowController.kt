package com.github.io.mangjoo.realworld.user.api

import com.github.io.mangjoo.realworld.auth.jwt.JwtDecode
import com.github.io.mangjoo.realworld.user.service.FollowUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class FollowController(
    private val followUseCase: FollowUseCase,
    private val jwtDecode: JwtDecode
) {

    @PostMapping("/api/profiles/{username}/follow")
    fun followApi(
        @PathVariable username: String,
        @RequestHeader("Authorization") token: String
    ) =
        jwtDecode.tokenToUserId(token)!!
            .let {
                ResponseEntity.ok(followUseCase.follow(username, it))
            }

}