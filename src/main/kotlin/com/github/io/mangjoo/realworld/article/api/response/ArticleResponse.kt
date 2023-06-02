package com.github.io.mangjoo.realworld.article.api.response

import com.fasterxml.jackson.annotation.JsonRootName
import com.github.io.mangjoo.realworld.article.domain.Article
import com.github.io.mangjoo.realworld.user.domain.User
import java.time.LocalDateTime

@JsonRootName("article")
data class ArticleResponse(
        val slug: String,
        val title: String,
        val description: String,
        val body: String,
        val tagList: Set<String>,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
        val favorited: Boolean,
        val favoritesCount: Int,
        val author: AuthorResponse
) {
    companion object {
        fun of(article: Article, user: User?): ArticleResponse {
            return ArticleResponse(
                    slug = article.slug,
                    title = article.title,
                    description = article.description,
                    body = article.body,
                    tagList = article.tags(),
                    createdAt = article.createdDate,
                    updatedAt = article.modifiedDate,
                    favorited = user?.let { article.isFavorite(it.username) } ?: false,
                    favoritesCount = article.favoriteCount(),
                    author = AuthorResponse.of(article.author, user?.isFollowing(article.author) ?: false)
            )
        }
    }
}

