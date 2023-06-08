package com.github.io.mangjoo.realworld.article.api.request.article

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.io.mangjoo.realworld.article.domain.Article
import com.github.io.mangjoo.realworld.user.domain.User

data class CreateArticleRequest(
    @JsonProperty("article")
    val createArticle: CreateArticle
) {
    fun toDomain(author: User) = Article(
        title = createArticle.title,
        description = createArticle.description,
        body = createArticle.body,
        tags = createArticle.tagList ?: mutableSetOf(),
        author = author
    )
}

data class CreateArticle(
    val title: String,
    val description: String,
    val body: String,
    val tagList: MutableSet<String>?
)