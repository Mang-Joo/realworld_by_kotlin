package com.github.io.mangjoo.realworld.article.api

import com.github.io.mangjoo.realworld.fixture.createArticle
import com.github.io.mangjoo.realworld.fixture.signUpUser
import com.github.io.mangjoo.realworld.user.api.SignUpController
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.*
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ArticleControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @Test
    fun getArticlesAPITest() {

        val signUpUser = mockMvc.signUpUser()
        mockMvc.createArticle(signUpUser.token!!)

        mockMvc.get("/api/articles") {
            contentType = APPLICATION_JSON
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.articlesCount") { value(1) }
            }
    }

    @Test
    fun getArticlesByFeedAPIErrorTest() {

        mockMvc.get("/api/articles/feed") {
            contentType = APPLICATION_JSON
        }
            .andExpect {
                status { is4xxClientError() }
            }
    }

    @Test
    fun getArticlesFeedAPITest() {
        val signUpUser = mockMvc.signUpUser()
        val createArticle = mockMvc.createArticle(signUpUser.token!!)
        val signUpUser1 = mockMvc.signUpUser(
            SignUpController.SignUpRequest(
                "test@gmail.com",
                "test",
                "test",
            )
        )

        mockMvc.post("/api/profiles/${signUpUser.userName}/follow") {
            contentType = APPLICATION_JSON
            header("Authorization", "Bearer ${signUpUser1.token}")
        }

        mockMvc.get("/api/articles/feed") {
            contentType = APPLICATION_JSON
            header("Authorization", "Bearer ${signUpUser1.token}")
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.articlesCount") { value(1) }
            }
    }

    @Test
    fun createArticleApiTest() {
        val signUpUser = mockMvc.signUpUser()

        mockMvc.post("/api/articles/") {
            contentType = APPLICATION_JSON
            header("Authorization", "Bearer ${signUpUser.token}")
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
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.article.title") { value("How to train your dragon") }
                jsonPath("$.article.slug") { value("How-to-train-your-dragon") }
                jsonPath("$.article.description") { value("Ever wonder how?") }
                jsonPath("$.article.body") { value("You have to believe") }
            }
    }

    @Test
    fun updateArticleApiTest() {
        val signUpUser = mockMvc.signUpUser()
        val createArticle = mockMvc.createArticle(signUpUser.token!!)

        mockMvc.put("/api/articles/${createArticle.slug}") {
            contentType = APPLICATION_JSON
            header("Authorization", "Bearer ${signUpUser.token}")
            content = """
                {
                        "title": "HoHoHo to train your dragon",
                        "description": "Ever wonder how?",
                        "body": "You have to believe"
                }
            """.trimIndent()
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.article.title") { value("HoHoHo to train your dragon") }
                jsonPath("$.article.slug") { value("HoHoHo-to-train-your-dragon") }
                jsonPath("$.article.description") { value("Ever wonder how?") }
                jsonPath("$.article.body") { value("You have to believe") }
            }
    }

    @Test
    fun deleteArticleApiTest() {
        val signUpUser = mockMvc.signUpUser()
        val createArticle = mockMvc.createArticle(signUpUser.token!!)

        mockMvc.delete("/api/articles/${createArticle.slug}") {
            contentType = APPLICATION_JSON
            header("Authorization", "Bearer ${signUpUser.token}")
        }
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    fun deleteFailTest() {
        val signUpUser = mockMvc.signUpUser()
        val createArticle = mockMvc.createArticle(signUpUser.token!!)

        mockMvc.delete("/api/articles/hihi") {
            contentType = APPLICATION_JSON
            header("Authorization", "Bearer ${signUpUser.token}")
        }
            .andExpect {
                status {
                    is4xxClientError()
                }
            }
    }

    @Test
    fun addFavoritedTest() {
        val signUpUser = mockMvc.signUpUser()
        val createArticle = mockMvc.createArticle(signUpUser.token!!)

        mockMvc.post("/api/articles/${createArticle.slug}/favorite") {
            contentType = APPLICATION_JSON
            header("Authorization", "Bearer ${signUpUser.token}")
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.article.favorited") { value(true) }
                jsonPath("$.article.favoritesCount") { value(1) }
            }
    }

    @Test
    fun unFavoriteTest() {
        val signUpUser = mockMvc.signUpUser()
        val createArticle = mockMvc.createArticle(signUpUser.token!!)

        mockMvc.post("/api/articles/${createArticle.slug}/favorite") {
            contentType = APPLICATION_JSON
            header("Authorization", "Bearer ${signUpUser.token}")
        }

        mockMvc.delete("/api/articles/${createArticle.slug}/favorite") {
            contentType = APPLICATION_JSON
            header("Authorization", "Bearer ${signUpUser.token}")
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.article.favorited") { value(false) }
                jsonPath("$.article.favoritesCount") { value(0) }
            }
    }
}