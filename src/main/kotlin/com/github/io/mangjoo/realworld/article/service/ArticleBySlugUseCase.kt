package com.github.io.mangjoo.realworld.article.service

import com.github.io.mangjoo.realworld.article.api.response.ArticleResponse
import com.github.io.mangjoo.realworld.article.repository.ArticleRepository
import org.springframework.stereotype.Service

interface ArticleBySlugUseCase {
    fun article(slug: String): ArticleResponse

    @Service
    class ArticleBySlug(
        private val articleRepository: ArticleRepository
    ): ArticleBySlugUseCase{
        override fun article(slug: String): ArticleResponse =
            articleRepository
                .findBySlug(slug)
                .let { ArticleResponse.of(it, null) }
    }

}