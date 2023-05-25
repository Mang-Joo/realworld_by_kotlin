package com.github.io.mangjoo.realworld.user.domain

import com.github.io.mangjoo.realworld.fixture.Fixture
import com.github.io.mangjoo.realworld.user.domain.vo.UserInfo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class UserTest {

    @Test
    fun updateUserInfoTest() {
        // Given
        val user = Fixture().user
        val update = UserInfo(
            email = "update@update.com",
            username = "update",
            bio = "update",
            image = "update",
        )
        // When
        val updateUserInfo = user.updateUserInfo(update)

        // Then
        assertThat(update).isEqualTo(updateUserInfo.userInfo)
    }

    @Test
    fun updatePasswordTest() {
        // Given
        val user = Fixture().user
        val updatePassword = "updatePassword"

        // When
        val updateUserPassword = user.updatePassword(updatePassword)

        // Then
        assertThat(updateUserPassword.password).isEqualTo(updatePassword)
    }

    @Test
    fun isFollowTest() {
        // Given
        val user = Fixture().user
        val followUser = Fixture().follower

        // Then
        assertThat(user.isFollow(followUser)).isEqualTo(true)
    }

    @Test
    fun followingTest() {
        // Given
        val user = Fixture().user
        val followUser = Fixture().follower2

        // When
        user.follow(followUser)

        // Then
        assertThat(user.isFollow(followUser)).isEqualTo(true)
        assertThat(followUser.followingCount()).isEqualTo(1)
    }

//    @Test
//    fun u
}