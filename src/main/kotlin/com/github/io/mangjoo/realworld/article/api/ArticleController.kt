package com.github.io.mangjoo.realworld.article.api

import com.github.io.mangjoo.realworld.article.api.request.GetArticleRequest
import com.github.io.mangjoo.realworld.article.api.request.PageRequest
import com.github.io.mangjoo.realworld.article.api.response.GetArticlesResponse
import com.github.io.mangjoo.realworld.article.service.GetArticlesUseCase
import com.github.io.mangjoo.realworld.auth.jwt.JwtDecode
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val jwtDecode: JwtDecode
) {

    @GetMapping("/api/articles")
    fun getArticles(
        @ModelAttribute getArticleRequest: GetArticleRequest,
        @RequestHeader("Authorization") token: String?,
        @PageableDefault pageRequest: PageRequest
    ): ResponseEntity<GetArticlesResponse> = ResponseEntity.ok()
        .body(
            getArticlesUseCase.getArticles(
                getArticleRequest.toFilterByArticles(),
                pageRequest,
                jwtDecode.tokenToUserId(token)
            )
        )


}