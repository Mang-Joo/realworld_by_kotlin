package com.github.io.mangjoo.realworld.article.repository

import com.github.io.mangjoo.realworld.article.domain.Comment
import com.github.io.mangjoo.realworld.config.exception.RealWorldException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository

interface CommentRepository {
    fun save(comment: Comment): Comment

    fun findByArticleSlug(slug: String): Collection<Comment>

    fun deleteByCommentId(commentId: Long): Long

    fun findByCommentId(commentId: Long): Comment

    @Repository
    class CommentRepositoryImpl(
        private val commentJpaRepository: CommentJpaRepository
    ) : CommentRepository {
        override fun save(comment: Comment): Comment =
            commentJpaRepository.save(comment)

        override fun findByArticleSlug(slug: String): Collection<Comment> =
            commentJpaRepository.findByArticleSlug(slug)

        override fun deleteByCommentId(commentId: Long): Long = commentJpaRepository
            .deleteById(commentId)
            .let { commentId }

        override fun findByCommentId(commentId: Long): Comment = commentJpaRepository
            .findByCommentId(commentId) ?: throw RealWorldException(HttpStatus.NOT_FOUND, "Comment not found")
    }
}