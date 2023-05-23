package com.github.io.mangjoo.realworld.user.api

import com.github.io.mangjoo.realworld.user.domain.vo.UserInfo
import com.github.io.mangjoo.realworld.user.service.UpdateUser
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class UpdateUserController(
    private val updateUser: UpdateUser
) {

    @PutMapping("/api/user")
    fun updateUser(@RequestBody updateUserRequest: UpdateUserRequest) = updateUser
        .updateUser(updateUserRequest.toDomain(), updateUserRequest.password)
        .let { ResponseEntity.ok(UpdateUserResponse.from(it)) }


    data class UpdateUserRequest(
        val email: String,
        val password: String,
        val username: String,
        val bio: String,
        val image: String
    ) {
        fun toDomain() =
            UserInfo(
                email = email,
                username = username,
                bio = bio,
                image = image
            )
    }

    data class UpdateUserResponse(
        val email: String,
        val userName: String,
        val bio: String,
        val image: String,
    ) {
        companion object {
            fun from(userInfo: UserInfo) =
                UpdateUserResponse(
                    email = userInfo.email,
                    userName = userInfo.username,
                    bio = userInfo.bio,
                    image = userInfo.image
                )
        }
    }
}