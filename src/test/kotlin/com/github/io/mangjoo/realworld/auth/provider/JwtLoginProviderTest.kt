package com.github.io.mangjoo.realworld.auth.provider

import com.github.io.mangjoo.realworld.auth.exception.PasswordNotMatchException
import com.github.io.mangjoo.realworld.auth.service.JwtUserDetailsService
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder

@ExtendWith(MockitoExtension::class)
class JwtLoginProviderTest {

    @InjectMocks
    private lateinit var jwtLoginProvider: JwtLoginProvider

    @Mock
    private lateinit var jwtUserDetailsService: JwtUserDetailsService

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @Test
    @DisplayName("비밀번호가 일치하지 않을 경우 예외를 던진다.")
    fun `Throw an exception if the passwords do not match`() {
        // given
        val email = "mangjoo@gmail.com"
        val password = "1234"
        given(jwtUserDetailsService.loadUserByUsername(email)).willReturn(Fixture().user)

        assertThatThrownBy { jwtLoginProvider.authenticate(UsernamePasswordAuthenticationToken(email, password)) }
            .isInstanceOf(PasswordNotMatchException::class.java)
            .hasMessage("Password not match")
    }

    @Test
    @DisplayName("비밀번호가 일치할 경우 토큰을 반환한다.")
    fun `If the password matches, a token is returned`() {
        // given
        val email = "mangjoo@gmail.com"
        val password = "password"
        given(jwtUserDetailsService.loadUserByUsername(email)).willReturn(Fixture().user)
        given(passwordEncoder.matches(password, Fixture().user.password)).willReturn(true)

        //when
        val authentication = jwtLoginProvider.authenticate(UsernamePasswordAuthenticationToken(email, password))

        //then
        assertThat(authentication.principal).isEqualTo(Fixture().user.id)
    }
}
