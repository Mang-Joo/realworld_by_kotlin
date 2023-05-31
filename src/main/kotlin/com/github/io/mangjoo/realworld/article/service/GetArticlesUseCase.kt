package com.github.io.mangjoo.realworld.article.service

import com.github.io.mangjoo.realworld.article.api.request.FilterByArticles
import com.github.io.mangjoo.realworld.article.api.response.ArticleResponse
import com.github.io.mangjoo.realworld.article.api.response.GetArticlesResponse
import com.github.io.mangjoo.realworld.article.repository.ArticleRepository
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface GetArticlesUseCase {
    fun getArticles(
        filterByArticles: FilterByArticles,
        pageRequest: PageRequest,
        userId: Long?
    ): GetArticlesResponse

    @Service
    class GetArticles(
        private val articleRepository: ArticleRepository,
        private val userRepository: UserRepository
    ) : GetArticlesUseCase {

        @Transactional(readOnly = true)
        override fun getArticles(
            filterByArticles: FilterByArticles,
            pageRequest: PageRequest,
            userId: Long?
        ): GetArticlesResponse {
            val articles = articleRepository.findAll(
                filterByArticles.tag,
                filterByArticles.author,
                filterByArticles.favorited,
                pageRequest
            )

            return userId?.let { id ->
                val user = userRepository.findById(id)
                GetArticlesResponse(articles.map { ArticleResponse.of(it, user) }, articles.size)
            } ?: return GetArticlesResponse(articles.map { ArticleResponse.of(it, null) }, articles.size)




            return GetArticlesResponse(mutableListOf(), 0)
        }
    }
}