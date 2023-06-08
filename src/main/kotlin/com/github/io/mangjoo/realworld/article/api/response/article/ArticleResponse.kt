package com.github.io.mangjoo.realworld.article.api.response.article

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.github.io.mangjoo.realworld.article.api.response.AuthorResponse
import com.github.io.mangjoo.realworld.article.domain.Article
import java.time.LocalDateTime

@JsonRootName("article")
data class ArticleResponse(
    val slug: String,
    val title: String,
    val description: String,
    val body: String,
    @get: JsonProperty("tagList")
    val tagList: Set<String>,
    @get: JsonProperty("createdAt")
    val createdAt: LocalDateTime,
    @get: JsonProperty("updatedAt")
    val updatedAt: LocalDateTime,
    val favorited: Boolean,
    @get: JsonProperty("favoritesCount")
    val favoritesCount: Int,
    val author: AuthorResponse
) {
    companion object {
        fun of(article: Article, userId: Long?): ArticleResponse {
            return ArticleResponse(
                slug = article.slug,
                title = article.title,
                description = article.description,
                body = article.body,
                tagList = article.tags(),
                createdAt = article.createdDate,
                updatedAt = article.modifiedDate,
                favorited = userId?.let { article.isFavorite(userId) } ?: false,
                favoritesCount = article.favoriteCount(),
                author = AuthorResponse.of(article.author, userId?.let { article.author.isFollower(userId) } ?: false)
            )
        }
    }
}

