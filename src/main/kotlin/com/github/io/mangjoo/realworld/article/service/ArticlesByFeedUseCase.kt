package com.github.io.mangjoo.realworld.article.service

import com.github.io.mangjoo.realworld.article.api.request.PageRequest
import com.github.io.mangjoo.realworld.article.api.response.ArticleResponse
import com.github.io.mangjoo.realworld.article.api.response.GetArticlesResponse
import com.github.io.mangjoo.realworld.article.repository.ArticleRepository
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.stereotype.Service

interface ArticlesByFeedUseCase {
    fun getArticlesByFeed(userId: Long, page: PageRequest): GetArticlesResponse

    @Service
    class ArticlesByFeedUseCaseImpl(
        private val articleRepository: ArticleRepository,
        private val userRepository: UserRepository
    ) : ArticlesByFeedUseCase {
        override fun getArticlesByFeed(userId: Long, page: PageRequest): GetArticlesResponse {
            val user = userRepository.findById(userId)
            val follows = user.followings.let { follows -> follows.map { it.toUser } }
                .let { userRepository.findAll(it) }
            return articleRepository.findFollowArticles(follows, page)
                .let { articles -> GetArticlesResponse(articles.map { ArticleResponse.of(it, user) }, articles.size) }
        }
    }
}