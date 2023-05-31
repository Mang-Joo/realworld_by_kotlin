package com.github.io.mangjoo.realworld.article.api

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
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
}