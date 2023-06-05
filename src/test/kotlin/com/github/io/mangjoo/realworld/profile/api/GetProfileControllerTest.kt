package com.github.io.mangjoo.realworld.profile.api

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
class GetProfileControllerTest(
    @Autowired private val mockMvc: MockMvc,
) {
    @Test
    @Transactional
    fun getProfileAPITest() {

        val singUpUser = mockMvc.signUpUser()

        mockMvc.get("/api/profiles/${singUpUser.userName}") {
            contentType = APPLICATION_JSON
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.profile.username") { value(singUpUser.userName) }
                jsonPath("$.profile.bio") { value(singUpUser.bio) }
                jsonPath("$.profile.image") { value(singUpUser.image) }
                jsonPath("$.profile.following") { value(false) }
            }
    }

}