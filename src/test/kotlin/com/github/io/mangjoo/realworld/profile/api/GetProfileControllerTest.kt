package com.github.io.mangjoo.realworld.profile.api

import com.github.io.mangjoo.realworld.fixture.singUpUser
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
class GetProfileControllerTest(
    @Autowired private val mockMvc: MockMvc,
) {
    @Test
    fun `getProfileAPITest`() {

        val singUpUser = mockMvc.singUpUser()

        mockMvc.get("/api/profiles/${singUpUser.userName}") {
            contentType = APPLICATION_JSON
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.username") { value(singUpUser.userName) }
                jsonPath("$.bio") { value(singUpUser.bio) }
                jsonPath("$.image") { value(singUpUser.image) }
                jsonPath("$.following") { value(false) }
            }
    }

}