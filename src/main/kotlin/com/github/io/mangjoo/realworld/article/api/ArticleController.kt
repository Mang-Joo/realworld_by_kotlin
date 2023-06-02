package com.github.io.mangjoo.realworld.article.api

import com.github.io.mangjoo.realworld.article.api.request.CreateArticleRequest
import com.github.io.mangjoo.realworld.article.api.request.GetArticleRequest
import com.github.io.mangjoo.realworld.article.api.request.PageRequest
import com.github.io.mangjoo.realworld.article.api.response.GetArticlesResponse
import com.github.io.mangjoo.realworld.article.service.ArticleBySlugUseCase
import com.github.io.mangjoo.realworld.article.service.ArticlesByFeedUseCase
import com.github.io.mangjoo.realworld.article.service.GetArticlesUseCase
import com.github.io.mangjoo.realworld.article.service.WriteArticleUseCase
import com.github.io.mangjoo.realworld.auth.jwt.JwtDecode
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ArticleController(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getArticlesByFeedUseCase: ArticlesByFeedUseCase,
    private val articleBySlugUseCase: ArticleBySlugUseCase,
    private val writeArticleUseCase: WriteArticleUseCase,
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

    @GetMapping("/api/articles/feed")
    fun getArticlesByFeed(
        @RequestHeader("Authorization") token: String,
        @PageableDefault pageRequest: PageRequest
    ): ResponseEntity<GetArticlesResponse> = ResponseEntity.ok()
        .body(
            getArticlesByFeedUseCase.getArticlesByFeed(
                jwtDecode.tokenToUserId(token)!!,
                pageRequest
            )
        )

    @GetMapping("/api/articles/{slug}")
    fun getArticle(
        @PathVariable slug: String,
    ) = articleBySlugUseCase.article(slug)
        .let { ResponseEntity.ok(it) }

    @PostMapping("/api/articles/")
    fun createArticle(
        @RequestBody createArticleRequest: CreateArticleRequest,
        @RequestHeader("Authorization") token: String
    ) = jwtDecode.tokenToUserId(token)!!
        .let { writeArticleUseCase.write(createArticleRequest, it) }
        .let { ResponseEntity.ok(it) }

}