package com.github.io.mangjoo.realworld.user.service

import com.github.io.mangjoo.realworld.user.domain.User
import com.github.io.mangjoo.realworld.user.exception.UserDuplicationException
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import com.github.io.mangjoo.realworld.user.service.UserRegist.*
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class RegistUseCaseTest {

    @InjectMocks
    lateinit var userRegist: UserRegistUsecase

    @Mock
    lateinit var userRepository: UserRepository

    private val registUseCaseRequest = RegistUseCaseRequest("mangjoo@gmail.com", "password", "mangjoo")

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

        // When
        val result = userRegist.regist(registUseCaseRequest)

        // Then
        assertThat(result.id).isEqualTo(user.id)
    }

    @Test
    fun save_WhenUserIsExists_ThenThrowException() {
        // Given
        given(userRepository.checkDuplication("mangjoo@gmail.com")).willReturn(true)

        assertThatThrownBy { userRegist.regist(registUseCaseRequest) }
            .isInstanceOf(UserDuplicationException::class.java)
    }
}