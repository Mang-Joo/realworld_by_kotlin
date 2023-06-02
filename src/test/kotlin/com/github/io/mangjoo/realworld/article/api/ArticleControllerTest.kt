package com.github.io.mangjoo.realworld.article.api

import com.github.io.mangjoo.realworld.fixture.signUpUser
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ArticleControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @Test
    fun getArticlesAPITest() {

        mockMvc.get("/api/articles") {
            contentType = APPLICATION_JSON
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.articlesCount") { value(0) }
                jsonPath("$.articles") { isEmpty() }
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

        mockMvc.get("/api/articles/feed") {
            contentType = APPLICATION_JSON
            header("Authorization", "Bearer ${signUpUser.token}")
        }
            .andExpect {
                status { isOk() }
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
}