package com.example.application.usecase

import com.example.application.port.output.FindAllUsers
import com.example.fixtures.builders.buildUser
import com.example.fixtures.builders.randomUserId
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetUsersUseCaseTest {
    private val findAllUsers = mockk<FindAllUsers>()

    private val useCase = GetUsersUseCase(findAllUsers)

    @BeforeEach
    fun setUp() = clearAllMocks()

    @Test
    fun `should get all users`() {
        // Given
        val (userId1, userId2) = randomUserId() to randomUserId()
        val users = listOf(buildUser(userId = userId1), buildUser(userId = userId2))
        every { findAllUsers() } returns users
        // When
        val result = useCase()
        // Then
        assertThat(result).isEqualTo(users)
        verify { findAllUsers() }
    }
}
