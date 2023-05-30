package com.github.io.mangjoo.realworld.article.api.request

data class GetArticleRequest(
    val tag: String?,
    val author: String?,
    val favorited: String?,
) {
}