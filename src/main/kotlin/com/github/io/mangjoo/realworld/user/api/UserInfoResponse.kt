package com.github.io.mangjoo.realworld.user.api

import com.github.io.mangjoo.realworld.user.domain.vo.Role
import com.github.io.mangjoo.realworld.user.domain.vo.UserInfo

data class UserInfoResponse(
    val email: String,
    val userName: String,
    val bio: String,
    val image: String,
    val role: Role,
    val token: String? = null
){
    companion object{
        fun from(userInfo: UserInfo) =
            UserInfoResponse(
                email = userInfo.email,
                userName = userInfo.username,
                bio = userInfo.bio,
                image = userInfo.image,
                role = userInfo.role,
                token = userInfo.token
            )
    }
}
