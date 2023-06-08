package com.github.io.mangjoo.realworld.article.api.response.article

data class GetArticlesResponse(
    val articles: List<ArticleResponse>,
    val articlesCount: Int
) {
}