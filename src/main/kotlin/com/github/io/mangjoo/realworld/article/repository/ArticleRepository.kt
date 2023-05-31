package com.github.io.mangjoo.realworld.article.repository

import com.github.io.mangjoo.realworld.article.domain.Article
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

interface ArticleRepository {

    fun save(article: Article): Article
    fun findById(id: Long): Article
    fun findAll(tag: String?, author: String?, favorited: String?, pageRequest: Pageable): Collection<Article>

    @Component
    class ArticleRepositoryImpl(
        private val articleJpaRepository: ArticleJpaRepository
    ): ArticleRepository {
        override fun save(article: Article): Article =
            articleJpaRepository.save(article)

        override fun findById(id: Long): Article =
            articleJpaRepository.findById(id)
                .orElseThrow { IllegalArgumentException("Article not found") }

        override fun findAll(tag: String?, author: String?, favorited: String?, pageRequest: Pageable): Collection<Article> =
            articleJpaRepository.findAll(
                author = author,
                tag = tag,
                favorited = favorited,
                pageable = pageRequest
            )
    }
}