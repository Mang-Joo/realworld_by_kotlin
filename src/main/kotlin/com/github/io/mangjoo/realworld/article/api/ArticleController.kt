package com.github.io.mangjoo.realworld.article.api

import com.github.io.mangjoo.realworld.article.api.request.GetArticleRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController {

    @GetMapping("/api/articles")
    fun getArticles(
        @ModelAttribute getArticleRequest: GetArticleRequest,
        @PageableDefault pageRequest: PageRequest,
        @RequestHeader("Authorization") token: String
    ) {

    }


}