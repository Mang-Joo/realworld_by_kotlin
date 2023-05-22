package com.github.io.mangjoo.realworld.user.service

import com.github.io.mangjoo.realworld.auth.config.JwtSupplier
import com.github.io.mangjoo.realworld.user.domain.User
import com.github.io.mangjoo.realworld.user.exception.UserException
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import com.github.io.mangjoo.realworld.user.service.UserSignUp.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@ExtendWith(MockitoExtension::class)
class SignUpUseCaseTest {

    @InjectMocks
    lateinit var userSignUpUseCase: UserSignUpUseCase

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var passwordEncoder: BCryptPasswordEncoder

    @Mock
    lateinit var jwtSupplier: JwtSupplier

    private val signUpUseCaseRequest = SignUpUseCaseRequest("mangjoo@gmail.com", "password", "mangjoo")

    @Test
    fun save_WhenUserIsNotExists_ThenSaveUser() {
        // Given
        given(userRepository.checkDuplication("mangjoo@gmail.com")).willReturn(false)
        val user = User(
            email = "mangjoo@gmail.com",
            password = "password",
            username = "mangjoo"
        )
        given(userRepository.save(user)).willReturn(user)
        given(passwordEncoder.encode("password")).willReturn("password")

        // When
        val result = userSignUpUseCase.signUp(signUpUseCaseRequest)

        // Then
        assertThat(result.email).isEqualTo(user.email)
    }

    @Test
    fun save_WhenUserIsExists_ThenThrowException() {
        // Given
        given(userRepository.checkDuplication("mangjoo@gmail.com")).willReturn(true)

        assertThatThrownBy { userSignUpUseCase.signUp(signUpUseCaseRequest) }
            .isInstanceOf(UserException::class.java)
    }
}