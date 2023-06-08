package com.github.io.mangjoo.realworld.article.service.comment

import com.github.io.mangjoo.realworld.article.api.response.comment.CommentResponse
import com.github.io.mangjoo.realworld.article.repository.CommentRepository
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.stereotype.Service

interface GetCommentsByArticle {
    fun get(slug: String, userId: Long?): Collection<CommentResponse>

    @Service
    class GetCommentsByArticleImpl(
        private val commentRepository: CommentRepository,
        private val userRepository: UserRepository
    ) : GetCommentsByArticle {
        override fun get(slug: String, userId: Long?): Collection<CommentResponse> = when (userId == null) {
            true -> commentRepository.findByArticleSlug(slug).map { comment -> CommentResponse.of(comment, null) }
            false -> userRepository.findById(userId)
                .let { commentRepository.findByArticleSlug(slug).map { comment -> CommentResponse.of(comment, it) } }
        }
    }
}