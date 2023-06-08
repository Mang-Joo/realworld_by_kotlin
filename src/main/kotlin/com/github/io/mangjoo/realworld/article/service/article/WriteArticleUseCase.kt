package com.github.io.mangjoo.realworld.article.service.article

import com.github.io.mangjoo.realworld.article.api.request.article.CreateArticleRequest
import com.github.io.mangjoo.realworld.article.api.response.article.ArticleResponse
import com.github.io.mangjoo.realworld.article.repository.ArticleRepository
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.stereotype.Service

interface WriteArticleUseCase {
    fun write(createArticleRequest: CreateArticleRequest, userId: Long): ArticleResponse

    @Service
    class WriteArticle(
        private val userRepository: UserRepository,
        private val articleRepository: ArticleRepository
    ) : WriteArticleUseCase {
        override fun write(createArticleRequest: CreateArticleRequest, userId: Long) =
            userRepository.findById(userId)
                .let { articleRepository.save(createArticleRequest.toDomain(it)) }
                .let { ArticleResponse.of(it, null) }
    }
}