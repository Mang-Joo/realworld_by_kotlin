package com.github.io.mangjoo.realworld.article.api

import com.github.io.mangjoo.realworld.article.api.request.PageRequest
import com.github.io.mangjoo.realworld.article.api.request.article.CreateArticleRequest
import com.github.io.mangjoo.realworld.article.api.request.article.GetArticleRequest
import com.github.io.mangjoo.realworld.article.api.request.article.UpdateArticleRequest
import com.github.io.mangjoo.realworld.article.api.response.article.GetArticlesResponse
import com.github.io.mangjoo.realworld.article.service.*
import com.github.io.mangjoo.realworld.article.service.article.*
import com.github.io.mangjoo.realworld.auth.jwt.JwtDecode
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ArticleController(
    private val findArticlesUseCase: FindArticlesUseCase,
    private val getFindArticlesByFeedUseCase: FindArticlesByFeedUseCase,
    private val findArticleBySlugUseCase: FindArticleBySlugUseCase,
    private val writeArticleUseCase: WriteArticleUseCase,
    private val updateArticleUseCase: UpdateArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val addFavoriteArticleUseCase: AddFavoriteArticleUseCase,
    private val unFavoriteUseCase: UnFavoriteUseCase,
    private val jwtDecode: JwtDecode
) {

    @GetMapping("/api/articles")
    fun getArticles(
        @ModelAttribute getArticleRequest: GetArticleRequest,
        @RequestHeader("Authorization") token: String?,
        @PageableDefault pageRequest: PageRequest
    ): ResponseEntity<GetArticlesResponse> = ResponseEntity.ok()
        .body(
            findArticlesUseCase.getArticles(
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
            getFindArticlesByFeedUseCase.getArticlesByFeed(
                jwtDecode.tokenToUserId(token)!!,
                pageRequest
            )
        )

    @GetMapping("/api/articles/{slug}")
    fun getArticle(
        @PathVariable slug: String,
    ) = findArticleBySlugUseCase.article(slug)
        .let { ResponseEntity.ok(it) }

    @PostMapping("/api/articles/")
    fun createArticle(
        @RequestBody createArticleRequest: CreateArticleRequest,
        @RequestHeader("Authorization") token: String
    ) = jwtDecode.tokenToUserId(token)!!
        .let { writeArticleUseCase.write(createArticleRequest, it) }
        .let { ResponseEntity.ok(it) }

    @PutMapping("/api/articles/{slug}")
    fun updateArticle(
        @PathVariable slug: String,
        @RequestBody updateArticleRequest: UpdateArticleRequest,
        @RequestHeader("Authorization") token: String
    ) = jwtDecode.tokenToUserId(token)!!
        .let { updateArticleUseCase.updateArticle(slug, updateArticleRequest, it) }
        .let { ResponseEntity.ok(it) }

    @DeleteMapping("/api/articles/{slug}")
    fun deleteArticle(
        @PathVariable slug: String,
        @RequestHeader("Authorization") token: String
    ) = jwtDecode.tokenToUserId(token)!!
        .let { deleteArticleUseCase.deleteArticle(slug, it) }
        .let { ResponseEntity.ok(it) }

    @PostMapping("/api/articles/{slug}/favorite")
    fun favoriteArticle(
        @PathVariable slug: String,
        @RequestHeader("Authorization") token: String
    ) = jwtDecode.tokenToUserId(token)!!
        .let { addFavoriteArticleUseCase.add(slug, it) }
        .let { ResponseEntity.ok(it) }

    @DeleteMapping("/api/articles/{slug}/favorite")
    fun unFavoriteArticle(
        @PathVariable slug: String,
        @RequestHeader("Authorization") token: String
    ) = jwtDecode.tokenToUserId(token)!!
        .let { unFavoriteUseCase.unFavorite(slug, it) }
        .let { ResponseEntity.ok(it) }


}