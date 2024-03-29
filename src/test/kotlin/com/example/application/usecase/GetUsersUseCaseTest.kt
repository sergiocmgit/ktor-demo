package com.example.application.usecase

import com.example.application.port.output.UserRepository
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
    private val userRepository = mockk<UserRepository>()

    private val useCase = GetUsersUseCase(userRepository)

    @BeforeEach
    fun setUp() = clearAllMocks()

    @Test
    fun `should get all users`() {
        // Given
        val (userId1, userId2) = randomUserId() to randomUserId()
        val users = listOf(buildUser(userId = userId1), buildUser(userId = userId2))
        every { userRepository.findAll() } returns users
        // When
        val result = useCase()
        // Then
        assertThat(result).isEqualTo(users)
        verify { userRepository.findAll() }
    }
}
