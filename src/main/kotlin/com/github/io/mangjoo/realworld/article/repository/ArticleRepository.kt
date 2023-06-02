package com.github.io.mangjoo.realworld.article.repository

import com.github.io.mangjoo.realworld.article.domain.Article
import com.github.io.mangjoo.realworld.user.domain.User
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

interface ArticleRepository {

    fun save(article: Article): Article
    fun findById(id: Long): Article
    fun findAll(tag: String?, author: String?, favorited: String?, pageRequest: Pageable): Collection<Article>
    fun findFollowArticles(users: Collection<User>, pageRequest: Pageable): Collection<Article>

    fun findBySlug(slug: String): Article

    @Component
    class ArticleRepositoryImpl(
        private val articleJpaRepository: ArticleJpaRepository
    ) : ArticleRepository {
        override fun save(article: Article): Article =
            articleJpaRepository.save(article)

        override fun findById(id: Long): Article =
            articleJpaRepository.findById(id)
                .orElseThrow { IllegalArgumentException("Article not found") }

        override fun findAll(
            tag: String?,
            author: String?,
            favorited: String?,
            pageRequest: Pageable
        ): Collection<Article> =
            articleJpaRepository.findAll(
                author = author,
                tag = tag,
                favorited = favorited,
                pageable = pageRequest
            )

        override fun findFollowArticles(users: Collection<User>, pageRequest: Pageable): Collection<Article> =
            articleJpaRepository.findByAuthorIn(users, pageRequest)

        override fun findBySlug(slug: String): Article =
            articleJpaRepository.findBySlug(slug)
            ?: throw IllegalArgumentException("Article not found")
    }
}