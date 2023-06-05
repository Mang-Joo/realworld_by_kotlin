package com.github.io.mangjoo.realworld.article.api.request

data class UpdateArticleRequest(
    val title: String,
    val description: String,
    val body: String,
)
