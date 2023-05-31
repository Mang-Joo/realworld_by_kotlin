package com.github.io.mangjoo.realworld.article.api.request

data class FilterByArticles(
    val author: String?,
    val tag: String?,
    val favorited: String?,
)
