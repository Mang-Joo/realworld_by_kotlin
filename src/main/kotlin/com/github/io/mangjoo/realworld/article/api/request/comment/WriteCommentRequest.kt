package com.github.io.mangjoo.realworld.article.api.request.comment

import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("comment")
data class WriteCommentRequest(
    val body: String
)