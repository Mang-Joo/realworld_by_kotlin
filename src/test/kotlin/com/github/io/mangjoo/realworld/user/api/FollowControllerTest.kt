package com.github.io.mangjoo.realworld.user.api

import com.github.io.mangjoo.realworld.fixture.signUpUser
import com.github.io.mangjoo.realworld.user.api.SignUpController.SignUpRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class FollowControllerTest(
    @Autowired private val mockMvc: MockMvc
) {
    @Test
    fun `followAPITest`() {
        val to = mockMvc.signUpUser()
        val from = mockMvc.signUpUser(SignUpRequest("test@gmail.com", "test", "test"))

        mockMvc.post("/api/profiles/${to.userName}/follow") {
            header("Authorization", "Bearer ${from.token}")
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.following") { value(true) }
            }
    }
}