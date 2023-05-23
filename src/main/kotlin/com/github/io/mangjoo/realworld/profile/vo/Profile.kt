package com.github.io.mangjoo.realworld.profile.vo

data class Profile(
    val username: String,
    val bio: String,
    val image: String,
    val following: Boolean = false
)
