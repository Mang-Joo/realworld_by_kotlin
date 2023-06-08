package com.github.io.mangjoo.realworld.article.service.comment

import com.github.io.mangjoo.realworld.article.api.response.comment.CommentResponse
import com.github.io.mangjoo.realworld.article.domain.Comment
import com.github.io.mangjoo.realworld.article.repository.ArticleRepository
import com.github.io.mangjoo.realworld.article.repository.CommentRepository
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.springframework.stereotype.Service

interface WriteCommentUseCase {
    fun write(slug: String, body: String, userId: Long): CommentResponse

    @Service
    class WriteComment(
        private val commentRepository: CommentRepository,
        private val articleRepository: ArticleRepository,
        private val userRepository: UserRepository
    ): WriteCommentUseCase {
        override fun write(slug: String, body: String, userId: Long): CommentResponse {
            val article = articleRepository.findBySlug(slug)
            val me = userRepository.findById(userId)
            val comment = Comment(body = body, article = article, author = me)
            return commentRepository.save(comment)
                .let { CommentResponse.of(it, me) }
        }
    }
}