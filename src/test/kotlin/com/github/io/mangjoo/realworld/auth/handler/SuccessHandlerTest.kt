package com.github.io.mangjoo.realworld.auth.handler

import com.github.io.mangjoo.realworld.user.domain.vo.Role.*
import com.github.io.mangjoo.realworld.auth.jwt.JwtCreate
import com.github.io.mangjoo.realworld.fixture.Fixture
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority

@ExtendWith(MockitoExtension::class)
class SuccessHandlerTest {

    @InjectMocks
    private lateinit var successHandler: SuccessHandler

    @Mock
    private lateinit var jwtCreate: JwtCreate

    @Test
    @DisplayName("성공 시 Jwt token을 response에 보내준다.")
    fun `In case of success, Jwt token is sent in response`() {
        //given
        given(jwtCreate.createToken(1L, SimpleGrantedAuthority(ROLE_USER.name))).willReturn(Fixture().userToken)
        val mockHttpServletResponse = MockHttpServletResponse()
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(1L, "password", listOf(SimpleGrantedAuthority(ROLE_USER.name)))

        //when
        successHandler.onAuthenticationSuccess(null, mockHttpServletResponse, usernamePasswordAuthenticationToken)

        //then
        assertThat(Fixture().userToken).isEqualTo(mockHttpServletResponse.contentAsString)
    }

}