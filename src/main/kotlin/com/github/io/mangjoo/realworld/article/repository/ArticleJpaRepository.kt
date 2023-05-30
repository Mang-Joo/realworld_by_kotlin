package com.github.io.mangjoo.realworld.article.repository

import com.github.io.mangjoo.realworld.article.domain.Article
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
        LEFT JOIN FETCH article.tags.group tags
        LEFT JOIN FETCH article.favorited favorited
        WHERE (:author IS NULL OR article.author.userInfo.username in (:author))
        AND (:tag IS NULL OR tags in (:tag))
        AND (:favorited IS NULL OR favorited in (:favorited))
    """
    )
    fun findAllH(
        @Param("author") author: String?,
        @Param("tag") tag: String?,
        @Param("favorited") favorited: String?,
        pageable: Pageable
    ): List<Article>
}