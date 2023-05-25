package com.github.io.mangjoo.realworld.user.api

import com.github.io.mangjoo.realworld.fixture.signUpUser
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class GetUserInfoControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @Test
    @Transactional
    fun `getUserInfoAPITest`() {
        val singUpUser = mockMvc.signUpUser()

        mockMvc.get("/api/user"){
            header("Authorization", "Bearer ${singUpUser.token}")
            contentType = APPLICATION_JSON
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.userName") { value(singUpUser.userName) }
                jsonPath("$.email") { value(singUpUser.email) }
                jsonPath("$.bio") { value(singUpUser.bio) }
                jsonPath("$.image") { value(singUpUser.image) }
            }
    }
}