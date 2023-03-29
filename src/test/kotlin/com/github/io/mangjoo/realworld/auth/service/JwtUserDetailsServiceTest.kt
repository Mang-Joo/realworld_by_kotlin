package com.github.io.mangjoo.realworld.auth.service

import com.github.io.mangjoo.realworld.auth.exception.UserNotFoundException
import com.github.io.mangjoo.realworld.auth.repository.UserRepository
import com.github.io.mangjoo.realworld.fixture.Fixture
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class JwtUserDetailsServiceTest {

    @InjectMocks
    private lateinit var jwtUserDetailsService: JwtUserDetailsService

    @Mock
    private lateinit var userRepository: UserRepository

    @Test
    @DisplayName("등록된 email을 입력하면 UserEntity를 반환한다.")
    fun `loadUserByUsername`() {
        // given
        val email = "mangjoo@gmail.com"
        given(userRepository.findByEmail(email)).willReturn(Fixture().userEntity)

        // when
        val user = jwtUserDetailsService.loadUserByUsername(email)

        //then
        assertThat(user).isEqualTo(Fixture().userEntity)
    }

    @Test
    @DisplayName("등록되지 않은 email을 입력하면 UserNotFoundException을 반환한다.")
    fun `loadUserByUsername - UserNotFoundException`() {
        // given
        val email = "mangjoo@gmail.com"
        given(userRepository.findByEmail(email)).willThrow(UserNotFoundException("User not found"))

        assertThatThrownBy { jwtUserDetailsService.loadUserByUsername(email) }
            .isInstanceOf(UserNotFoundException::class.java)
            .hasMessage("User not found")
    }
}