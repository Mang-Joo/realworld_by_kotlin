package com.github.io.mangjoo.realworld.article.repository

import com.github.io.mangjoo.realworld.article.domain.Article
import com.github.io.mangjoo.realworld.user.domain.User
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ArticleJpaRepository : JpaRepository<Article, Long> {

    @Query(
        """
        SELECT article 
        FROM Article article 
        LEFT JOIN FETCH article.author author
        LEFT JOIN FETCH article.tags.group tags
        LEFT JOIN FETCH article.favorited favorited
        WHERE (:author IS NULL OR author.userInfo.username in (:author))
        AND (:tag IS NULL OR tags in (:tag))
        AND (:favorited IS NULL OR favorited in (:favorited))
        ORDER BY article.createdDate DESC
     """
    )
    fun findAll(
        @Param("author") author: String?,
        @Param("tag") tag: String?,
        @Param("favorited") favorited: String?,
        pageable: Pageable
    ): List<Article>

    fun findByAuthorIn(
        authors: Collection<User>,
        pageable: Pageable
    ): List<Article>

    fun findBySlug(slug: String): Article?
    fun deleteBySlug(slug: String)
}