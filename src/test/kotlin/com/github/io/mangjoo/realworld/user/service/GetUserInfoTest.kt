package com.github.io.mangjoo.realworld.user.service

import com.github.io.mangjoo.realworld.fixture.Fixture
import com.github.io.mangjoo.realworld.user.exception.UserException
import com.github.io.mangjoo.realworld.user.repository.UserRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class GetUserInfoTest {

    @InjectMocks
    lateinit var getUserInfoUseCase: GetUserInfo.GetUserInfoUseCaseRequest

    @Mock
    lateinit var userRepository: UserRepository

    @Test
    fun findUser_WhenUserIsExists_ThenReturnUserInfo() {
        // Given
        val user = Fixture().user
        given(userRepository.findById(user.id)).willReturn(user)

        // When
        val userInfo = getUserInfoUseCase.findUser(0L)

        // Then
        assertThat(userInfo).isEqualTo(user.userInfo)
    }

    @Test
    fun findUser_WhenUserIsNotExists_ThenThrowException() {
        // Given
        given(userRepository.findById(1)).willThrow(UserException::class.java)

        assertThatThrownBy { getUserInfoUseCase.findUser(1) }
            .isInstanceOf(UserException::class.java)
    }
}