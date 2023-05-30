package com.github.io.mangjoo.realworld.article.repository

import com.github.io.mangjoo.realworld.article.domain.Article

interface ArticleRepository {

    fun save(article: Article): Article
    fun findById(id: Long): Article
    fun findAll(tag: String?, author: String?, favorited: String?): Collection<Article>

    class ArticleRepositoryImpl(
        private val articleJpaRepository: ArticleJpaRepository
    ): ArticleRepository {
        override fun save(article: Article): Article =
            articleJpaRepository.save(article)

        override fun findById(id: Long): Article =
            articleJpaRepository.findById(id)
                .orElseThrow { IllegalArgumentException("Article not found") }

        override fun findAll(tag: String?, author: String?, favorited: String?): Collection<Article> {
            TODO()
        }
    }
}