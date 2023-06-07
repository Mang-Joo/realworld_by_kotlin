package com.github.io.mangjoo.realworld.article.service

import com.github.io.mangjoo.realworld.article.repository.ArticleRepository
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.stereotype.Service

interface DeleteArticleUseCase {
    fun deleteArticle(slug: String, userId: Long): String

    @Service
    class DeleteArticleService(
        private val articleRepository: ArticleRepository,
        private val userRepository: UserRepository
    ) : DeleteArticleUseCase {
        override fun deleteArticle(slug: String, userId: Long) = userRepository
            .let { articleRepository.findBySlug(slug) }
            .let {
                when (userRepository.findById(userId) == it.author) {
                    true -> articleRepository.deleteBySlug(slug)
                    false -> throw IllegalStateException("You can't delete this article")
                }
            }
    }
}