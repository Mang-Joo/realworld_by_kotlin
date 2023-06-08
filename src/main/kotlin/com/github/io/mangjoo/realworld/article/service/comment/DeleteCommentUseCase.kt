package com.github.io.mangjoo.realworld.article.service.comment

import com.github.io.mangjoo.realworld.article.repository.CommentRepository
import com.github.io.mangjoo.realworld.config.exception.RealWorldException
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.stereotype.Service

interface DeleteCommentUseCase {
    fun delete(commentId: Long, requestUserId: Long, slug: String): Long

    @Service
    class DeleteCommentService(
        private val commentRepository: CommentRepository,
        private val userRepository: UserRepository,
    ) : DeleteCommentUseCase {
        override fun delete(commentId: Long, requestUserId: Long, slug: String) = commentRepository
            .findByCommentId(commentId)
            .takeIf { it.isNormal(requestUserId, slug) }
            ?.let { commentRepository.deleteByCommentId(commentId) }
            ?: throw RealWorldException(FORBIDDEN, "You can't delete this comment")
    }
}