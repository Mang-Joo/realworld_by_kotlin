package com.github.io.mangjoo.realworld.article.service.article

import com.github.io.mangjoo.realworld.article.api.response.article.ArticleResponse
import com.github.io.mangjoo.realworld.article.repository.ArticleRepository
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface AddFavoriteArticleUseCase {
    fun add(slug: String, userId: Long): ArticleResponse

    @Service
    @Transactional
    class AddFavoriteArticle(
        private val articleRepository: ArticleRepository,
        private val userRepository: UserRepository
    ) : AddFavoriteArticleUseCase {
        override fun add(slug: String, userId: Long): ArticleResponse = articleRepository
            .findBySlug(slug)
            .let {
                when (it.isFavorite(userId)) {
                    true -> throw IllegalStateException("You already added this article")
                    false -> it.addFavorite(userId)
                } }
            .let { ArticleResponse.of(it, userId) }
    }
}