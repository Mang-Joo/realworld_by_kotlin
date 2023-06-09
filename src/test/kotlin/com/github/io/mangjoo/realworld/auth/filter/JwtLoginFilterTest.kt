package com.github.io.mangjoo.realworld.auth.filter

import com.github.io.mangjoo.realworld.auth.exception.UserNotFoundException
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpMethod.*
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

class JwtLoginFilterTest {

    private val jwtLoginFilter = JwtLoginFilter(
        AntPathRequestMatcher("/api/user/login", POST.name()),
        null,
        null
    )

    @Test
    @DisplayName("Content-Type이 application/json이 아닌 경우 예외를 던진다.")
    fun `not application json throw test`() {
        // given
        val mockHttpServletRequest = MockHttpServletRequest()
            .apply { contentType = "text/plain" }
        val mockHttpServletResponse = MockHttpServletResponse()

        assertThatThrownBy { jwtLoginFilter.attemptAuthentication(mockHttpServletRequest, mockHttpServletResponse) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    @DisplayName("body가 username이 아닌 경우 예외가 발생한다.")
    fun `parameter not username then throw`() {
        // given
        val mockHttpServletRequest = MockHttpServletRequest()
            .apply {
                contentType = "application/json"
                setContent(
                    """
                    {
                        "username2": "mangjoo",
                        "password": "1234"
                    }
                """.trimIndent().toByteArray()
                )
            }
        val mockHttpServletResponse = MockHttpServletResponse()

        assertThatThrownBy { jwtLoginFilter.attemptAuthentication(mockHttpServletRequest, mockHttpServletResponse) }
            .isInstanceOf(UserNotFoundException::class.java)
    }

    @Test
    @DisplayName("body가 password가 아닌 경우 예외가 발생한다.")
    fun `parameter not password then throw`() {
        // given
        val mockHttpServletRequest = MockHttpServletRequest()
            .apply {
                contentType = "application/json"
                setContent(
                    """
                    {
                        "username": "mangjoo",
                        "password2": "1234"
                    }
                """.trimIndent().toByteArray()
                )
            }
        val mockHttpServletResponse = MockHttpServletResponse()

        assertThatThrownBy { jwtLoginFilter.attemptAuthentication(mockHttpServletRequest, mockHttpServletResponse) }
            .isInstanceOf(UserNotFoundException::class.java)
    }

    @Test
    @DisplayName("정상적인 요청일 경우 Authentication을 return한다")
    fun `not throw`() {
        // given
        val mockHttpServletRequest = MockHttpServletRequest()
            .apply {
                contentType = "application/json"
                characterEncoding = "UTF-8"
                setContent(
                    """
                    {
                        "username": "mangjoo",
                        "password": "1234"
                    }
                    """.toByteArray()
                )
                reader
            }
        val mockHttpServletResponse = MockHttpServletResponse()
        val mock = mock(AuthenticationManager::class.java)
        `when`(mock.authenticate(any(UsernamePasswordAuthenticationToken::class.java)))
            .thenReturn(UsernamePasswordAuthenticationToken("mangjoo", "1234"))
        jwtLoginFilter.setAuthenticationManager(mock)

        assertThat(jwtLoginFilter.attemptAuthentication(mockHttpServletRequest, mockHttpServletResponse))
            .isEqualTo(UsernamePasswordAuthenticationToken("mangjoo", "1234"))
    }
}