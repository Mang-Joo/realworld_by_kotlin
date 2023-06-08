package com.github.io.mangjoo.realworld.article.service.article

import com.github.io.mangjoo.realworld.article.api.request.article.FilterByArticles
import com.github.io.mangjoo.realworld.article.api.response.article.ArticleResponse
import com.github.io.mangjoo.realworld.article.api.response.article.GetArticlesResponse
import com.github.io.mangjoo.realworld.article.repository.ArticleRepository
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface FindArticlesUseCase {
    fun getArticles(
        filterByArticles: FilterByArticles,
        pageRequest: Pageable,
        userId: Long?
    ): GetArticlesResponse

    @Service
    class FindArticles(
        private val articleRepository: ArticleRepository,
        private val userRepository: UserRepository
    ) : FindArticlesUseCase {

        @Transactional(readOnly = true)
        override fun getArticles(
            filterByArticles: FilterByArticles,
            pageRequest: Pageable,
            userId: Long?
        ): GetArticlesResponse {
            val articles = articleRepository.findAll(
                filterByArticles.tag,
                filterByArticles.author,
                filterByArticles.favorited,
                pageRequest
            )

            return userId?.let { id ->
                GetArticlesResponse(articles.map { ArticleResponse.of(it, id) }, articles.size)
            } ?: return GetArticlesResponse(articles.map { ArticleResponse.of(it, null) }, articles.size)
        }
    }
}