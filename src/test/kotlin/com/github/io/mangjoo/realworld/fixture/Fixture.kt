package com.github.io.mangjoo.realworld.fixture

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.io.mangjoo.realworld.article.api.response.ArticleResponse
import com.github.io.mangjoo.realworld.article.domain.Article
import com.github.io.mangjoo.realworld.user.api.SignUpController.SignUpRequest
import com.github.io.mangjoo.realworld.user.api.UserInfoResponse
import com.github.io.mangjoo.realworld.user.domain.User
import com.github.io.mangjoo.realworld.user.domain.vo.Role.ROLE_USER
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

class Fixture {

    val user = User(
        "mangjoo@gmail.com",
        "password",
        "mangjoo",
        "bio",
        "image",
        true,
        ROLE_USER
    )

    val follower = User("follower@gmail.com", "password", "follower")
        .apply { this.follow(user) }

    val follower2 = User("followe22r@gmail.com", "password22", "follower22")

    val article = Article(
        "title",
        "description",
        "body",
        mutableSetOf("tag1", "tag2"),
        user
    )

    val userToken: String =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlJPTEVfVVNFUiJ9.n_Voazt4-FHnoVxrvnCSIDCepqK8cd-vEQYZLbsZ2Hc"

}

fun MockMvc.signUpUser(
    request: SignUpRequest = SignUpRequest("mangjoo@email.com", "password", "mangjoo"),
): UserInfoResponse {
    return post("/api/user") {
        contentType = MediaType.APPLICATION_JSON
        content = jacksonObjectMapper().writeValueAsString(request)
    }.andReturn().response.contentAsString.let { jacksonObjectMapper().readValue(it, UserInfoResponse::class.java) }
}

fun MockMvc.createArticle(
    token: String
): ArticleResponse {
    return post("/api/articles/") {
        contentType = MediaType.APPLICATION_JSON
        header("Authorization", "Bearer $token")
        content = """
                {
                    "article": {
                        "title": "How to train your dragon",
                        "description": "Ever wonder how?",
                        "body": "You have to believe",
                        "tagList": ["reactjs", "angularjs", "dragons"]
                    }
                }
            """.trimIndent()
    }.andReturn().response.contentAsString.let {
        ObjectMapper().registerModule(JavaTimeModule()).registerKotlinModule()
            .enable(SerializationFeature.WRAP_ROOT_VALUE).enable(DeserializationFeature.UNWRAP_ROOT_VALUE)
            .readValue(it, ArticleResponse::class.java)
    }
}