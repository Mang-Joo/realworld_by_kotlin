package com.github.io.mangjoo.realworld.article.api

import com.github.io.mangjoo.realworld.fixture.createArticle
import com.github.io.mangjoo.realworld.fixture.signUpUser
import com.github.io.mangjoo.realworld.fixture.writeComment
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CommentControllerTest(
    @Autowired private val mockMvc: MockMvc
) {
    @Test
    fun writeCommentTest() {
        val signUpUser = mockMvc.signUpUser()
        val token = signUpUser.token!!
        val createArticle = mockMvc.createArticle(token)

        mockMvc.post("/api/articles/${createArticle.slug}/comments") {
            header("Authorization", "Bearer $token")
            contentType = APPLICATION_JSON
            content = """
                {
                    "comment": {
                        "body": "comment body"
                    }
                }
            """.trimIndent()
        }.andExpect {
            status { isOk() }
            jsonPath("$.comment.body") { value("comment body") }
        }
    }

    @Test
    fun getComments() {
        val signUpUser = mockMvc.signUpUser()
        val token = signUpUser.token!!
        val createArticle = mockMvc.createArticle(token)
        val writeComment = mockMvc.writeComment(token, createArticle.slug)

        mockMvc.get("/api/articles/${createArticle.slug}/comments")
            .andExpect {
                status { isOk() }
                jsonPath("$.size()") { value(1) }
            }
            .andReturn()
            .response
            .contentAsString
            .also { println(it) }
    }

    @Test
    fun deleteCommentsTest() {
        val signUpUser = mockMvc.signUpUser()
        val token = signUpUser.token!!
        val createArticle = mockMvc.createArticle(token)
        val writeComment = mockMvc.writeComment(token, createArticle.slug)

        mockMvc
            .delete("/api/articles/${createArticle.slug}/comments/${writeComment.id}") {
            headers { setBearerAuth(token) }
        }
            .andExpect { status { isOk() } }
    }
}