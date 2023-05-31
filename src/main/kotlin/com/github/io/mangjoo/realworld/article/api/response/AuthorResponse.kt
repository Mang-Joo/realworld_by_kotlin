package com.github.io.mangjoo.realworld.article.api.response

import com.github.io.mangjoo.realworld.user.domain.User

data class AuthorResponse(
    val username: String,
    val bio: String?,
    val image: String?,
    val following: Boolean
) {
    companion object {
        fun of(user: User, isFollowing: Boolean): AuthorResponse {
            return AuthorResponse(user.username, user.bio, user.image, isFollowing)
        }
    }
}

