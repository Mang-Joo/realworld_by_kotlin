package com.github.io.mangjoo.realworld.article.api.request.article

data class GetArticleRequest(
    val tag: String?,
    val author: String?,
    val favorited: String?,
) {
    fun toFilterByArticles(): FilterByArticles = FilterByArticles(tag, author, favorited)
}