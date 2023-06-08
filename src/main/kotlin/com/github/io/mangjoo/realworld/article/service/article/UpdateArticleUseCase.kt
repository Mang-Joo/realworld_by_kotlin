package com.github.io.mangjoo.realworld.article.service.article

import com.github.io.mangjoo.realworld.article.api.request.article.UpdateArticleRequest
import com.github.io.mangjoo.realworld.article.api.response.article.ArticleResponse
import com.github.io.mangjoo.realworld.article.repository.ArticleRepository
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface UpdateArticleUseCase {
    fun updateArticle(slug: String, updateArticle: UpdateArticleRequest, userId: Long): ArticleResponse

    @Service
    @Transactional
    class UpdateArticleService(
        private val articleRepository: ArticleRepository,
        private val userRepository: UserRepository
    ) : UpdateArticleUseCase {
        override fun updateArticle(slug: String, updateArticle: UpdateArticleRequest, userId: Long): ArticleResponse {
            val user = userRepository.findById(userId)
            val article = articleRepository.findBySlug(slug)
            if (user != article.author) {
                throw IllegalStateException("You can't update this article")
            }
            val updatedArticle = article.update(
                updateArticle.title,
                updateArticle.body,
                updateArticle.description,
            )
            return ArticleResponse.of(updatedArticle, null)
        }
    }
}