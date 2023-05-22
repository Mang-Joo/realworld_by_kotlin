package com.github.io.mangjoo.realworld.user.api

import com.github.io.mangjoo.realworld.user.domain.vo.UserInfo
import com.github.io.mangjoo.realworld.user.service.FindUser
import org.slf4j.LoggerFactory.*
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class FindUserController(
    private val findUser: FindUser,
    private val jwtDecoder: JwtDecoder
) {
    @GetMapping("/api/user")
    fun findUser(@RequestHeader("Authorization") authorization: String) = jwtDecoder
        .decode(authorization.removePrefix("Bearer ")).subject
        .let { findUser.findUser(it.toLong()) }
        .let { ResponseEntity.ok(FindUserResponse.from(it)) }

    data class FindUserResponse(
        val email: String,
        val userName: String,
        val bio: String,
        val image: String,
    ){
        companion object{
            fun from(userInfo: UserInfo) =
                FindUserResponse(
                    email = userInfo.email,
                    userName = userInfo.username,
                    bio = userInfo.bio,
                    image = userInfo.image
                )
        }
    }
}