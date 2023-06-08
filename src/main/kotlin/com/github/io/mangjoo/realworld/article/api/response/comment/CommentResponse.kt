package com.github.io.mangjoo.realworld.article.api.response.comment

import com.fasterxml.jackson.annotation.JsonRootName
import com.github.io.mangjoo.realworld.article.api.response.AuthorResponse
import com.github.io.mangjoo.realworld.article.domain.Comment
import com.github.io.mangjoo.realworld.user.domain.User
import java.time.LocalDateTime

@JsonRootName("comment")
data class CommentResponse(
    val id: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val body: String,
    val author: AuthorResponse
){
    companion object {
        fun of(comment: Comment, me: User?): CommentResponse =
            CommentResponse(
                id = comment.id,
                createdAt = comment.createdDate,
                updatedAt = comment.modifiedDate,
                body = comment.body,
                author = AuthorResponse.of(comment.author, comment.author.isFollower(me?.id ?: 0))
            )
    }

    override fun toString(): String {
        return "CommentResponse(id=$id, createdAt=$createdAt, updatedAt=$updatedAt, body='$body', author=$author)"
    }


}
