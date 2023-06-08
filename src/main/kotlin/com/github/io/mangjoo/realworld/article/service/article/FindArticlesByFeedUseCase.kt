package com.github.io.mangjoo.realworld.article.service.article

import com.github.io.mangjoo.realworld.article.api.request.PageRequest
import com.github.io.mangjoo.realworld.article.api.response.article.ArticleResponse
import com.github.io.mangjoo.realworld.article.api.response.article.GetArticlesResponse
import com.github.io.mangjoo.realworld.article.repository.ArticleRepository
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.stereotype.Service

interface FindArticlesByFeedUseCase {
    fun getArticlesByFeed(userId: Long, page: PageRequest): GetArticlesResponse

    @Service
    class ArticlesByFeedUseCaseImpl(
        private val articleRepository: ArticleRepository,
        private val userRepository: UserRepository
    ) : FindArticlesByFeedUseCase {
        override fun getArticlesByFeed(userId: Long, page: PageRequest): GetArticlesResponse {
            val user = userRepository.findById(userId)
            val follows = user.followings.let { follows -> follows.map { it.toUser } }
                .let { userRepository.findAll(it) }
            return articleRepository.findFollowArticles(follows, page)
                .let { articles -> GetArticlesResponse(articles.map { ArticleResponse.of(it, userId) }, articles.size) }
        }
    }
}