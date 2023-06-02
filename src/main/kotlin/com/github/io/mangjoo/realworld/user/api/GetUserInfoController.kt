package com.github.io.mangjoo.realworld.user.api

import com.github.io.mangjoo.realworld.auth.jwt.JwtDecode
import com.github.io.mangjoo.realworld.user.domain.vo.UserInfo
import com.github.io.mangjoo.realworld.user.service.GetUserInfo
import org.slf4j.LoggerFactory.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class GetUserInfoController(
    private val getUserInfo: GetUserInfo,
    private val jwtDecode: JwtDecode
) {
    @GetMapping("/api/user")
    fun getUserInfo(@RequestHeader("Authorization") authorization: String) = jwtDecode
        .tokenToUserId(authorization)!!
        .let { getUserInfo.findUser(it) }
        .let { ResponseEntity.ok(GetUserInfoResponse.from(it)) }

    data class GetUserInfoResponse(
        val email: String,
        val userName: String,
        val bio: String,
        val image: String,
    ){
        companion object{
            fun from(userInfo: UserInfo) =
                GetUserInfoResponse(
                    email = userInfo.email,
                    userName = userInfo.username,
                    bio = userInfo.bio,
                    image = userInfo.image
                )
        }
    }
}