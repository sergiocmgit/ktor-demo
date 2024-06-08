package com.example.application.domain.service

import com.example.application.domain.UserId
import com.example.application.domain.UserInvalidStatus
import com.example.application.domain.UserStatus.DEACTIVATED
import com.example.application.port.output.FindUserByUserId
import com.example.fixtures.builders.DEFAULT_USER_ID
import com.example.fixtures.builders.buildUser
import com.example.fixtures.isLeftWith
import com.example.fixtures.isRightWith
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GetActiveUserTest {
    private val findUserByUserId = mockk<FindUserByUserId>()
    private val getActiveUser = GetActiveUser(findUserByUserId)

    private val userId = UserId(DEFAULT_USER_ID)
    private val user = buildUser(userId = userId.value)

    @Test
    fun `should get an active user`() {
        // Given
        every { findUserByUserId(userId) } returns user
        // When
        val result = getActiveUser(userId)
        // Then
        assertThat(result).isRightWith(user)
    }

    // TODO: missing test for UserNotFound

    @Test
    fun `should fail when the user is not active`() {
        // Given
        every { findUserByUserId(userId) } returns user.copy(status = DEACTIVATED)
        // When
        val result = getActiveUser(userId)
        // Then
        assertThat(result).isLeftWith(UserInvalidStatus)
    }
}
