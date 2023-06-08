package com.github.io.mangjoo.realworld.article.service.article

import com.github.io.mangjoo.realworld.article.api.response.article.ArticleResponse
import com.github.io.mangjoo.realworld.article.repository.ArticleRepository
import org.springframework.stereotype.Service

interface FindArticleBySlugUseCase {
    fun article(slug: String): ArticleResponse

    @Service
    class FindArticleBySlug(
        private val articleRepository: ArticleRepository
    ): FindArticleBySlugUseCase {
        override fun article(slug: String): ArticleResponse =
            articleRepository
                .findBySlug(slug)
                .let { ArticleResponse.of(it, null) }
    }

}