package com.github.io.mangjoo.realworld.user.api

import com.github.io.mangjoo.realworld.fixture.Fixture
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SignUpControllerTest @Autowired constructor(
    private val mockMvc: MockMvc
) {

    @Test
    @Transactional
    fun `signUpAPITest`() {
        val user = Fixture().user
        mockMvc.post("/api/user") {
            contentType = APPLICATION_JSON
            content = """
                {                    
                    "email": "mangjoo@gmail.com",
                    "password": "1234",
                    "username": "mangjoo"
                }   
                    """
        }.andExpect {
            status { isOk() }
            jsonPath("$.email") { value(user.email) }
            jsonPath("$.userName") { value(user.username) }
            jsonPath("$.bio") { value("") }
            jsonPath("$.image") { value("") }
            jsonPath("$.token") { isNotEmpty() }
        }
    }
}