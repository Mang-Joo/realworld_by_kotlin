package com.github.io.mangjoo.realworld.article.repository

import com.github.io.mangjoo.realworld.article.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CommentJpaRepository: JpaRepository<Comment, Long> {
    @Query(
        """
            SELECT c
            FROM Comment c
            JOIN FETCH c.article a
            JOIN FETCH c.author au
            WHERE c.article.slug = :slug
        """
    )
    fun findByArticleSlug(slug: String): Collection<Comment>

    @Query(
        """
            SELECT c
            FROM Comment c
            JOIN FETCH c.article a
            JOIN FETCH c.author au
            WHERE c.id = :commentId
        """
    )
    fun findByCommentId(commentId: Long): Comment?
}