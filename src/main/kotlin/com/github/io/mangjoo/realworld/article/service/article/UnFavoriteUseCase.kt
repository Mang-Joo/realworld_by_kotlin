package com.github.io.mangjoo.realworld.article.service.article

import com.github.io.mangjoo.realworld.article.api.response.article.ArticleResponse
import com.github.io.mangjoo.realworld.article.repository.ArticleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface UnFavoriteUseCase {
    fun unFavorite(slug: String, requestUser: Long): ArticleResponse

    @Service
    @Transactional
    class UnFavoriteArticle(
        private val articleRepository: ArticleRepository,
    ) : UnFavoriteUseCase {
        override fun unFavorite(slug: String, requestUser: Long): ArticleResponse = articleRepository
            .findBySlug(slug)
            .let {
                when (it.isFavorite(requestUser)) {
                    true -> it.unFavorite(requestUser)
                    false -> it
                }
            }
            .let { ArticleResponse.of(it, requestUser) }
    }
}