package com.github.io.mangjoo.realworld.article.api

import com.github.io.mangjoo.realworld.article.api.request.comment.WriteCommentRequest
import com.github.io.mangjoo.realworld.article.service.comment.DeleteCommentUseCase
import com.github.io.mangjoo.realworld.article.service.comment.GetCommentsByArticle
import com.github.io.mangjoo.realworld.article.service.comment.WriteCommentUseCase
import com.github.io.mangjoo.realworld.auth.jwt.JwtDecode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CommentController(
    private val writeCommentUseCase: WriteCommentUseCase,
    private val getCommentsByArticle: GetCommentsByArticle,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val jwtDecode: JwtDecode
) {

    @PostMapping("/api/articles/{slug}/comments")
    fun writeComment(
        @PathVariable slug: String,
        @RequestBody writeCommentRequest: WriteCommentRequest,
        @RequestHeader("Authorization") token: String
    ) = writeCommentUseCase
        .write(slug, writeCommentRequest.body, jwtDecode.tokenToUserId(token)!!)
        .let { ResponseEntity.ok(it) }

    @GetMapping("/api/articles/{slug}/comments")
    fun getComments(
        @PathVariable slug: String,
        @RequestHeader("Authorization") token: String?
    ) = getCommentsByArticle.get(slug, jwtDecode.tokenToUserId(token))
        .let { ResponseEntity.ok(it) }

    @DeleteMapping("/api/articles/{slug}/comments/{id}")
    fun deleteComment(
        @PathVariable slug: String,
        @PathVariable id: Long,
        @RequestHeader("Authorization") token: String
    ) = deleteCommentUseCase.delete(id, jwtDecode.tokenToUserId(token)!!, slug)
        .let { ResponseEntity.ok(it) }

}