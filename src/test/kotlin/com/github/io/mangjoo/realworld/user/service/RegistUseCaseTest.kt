package com.github.io.mangjoo.realworld.user.service

import com.github.io.mangjoo.realworld.user.repository.UserEntity
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

    @Test
    fun save_WhenUserIsNotExists_ThenSaveUser() {
        // Given
        given(userRepository.checkDuplication("mangjoo@gmail.com")).willReturn(false)
        given(userRepository.save(UserEntity(
            email = "mangjoo@gmail.com",
            password = "password",
            username = "mangjoo"
        ))).willReturn(1L)
        val registUseCaseRequest = RegistUseCaseRequest("mangjoo@gmail.com", "password", "mangjoo")

        // When
        val result = userRegist.regist(registUseCaseRequest)

        // Then
        assertThat(result).isEqualTo(1L)
    }
}