package com.example.application.usecase

import com.example.application.port.output.FindAllScooters
import com.example.fixtures.builders.buildScooter
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetScootersUseCaseTest {
    private val findAllScooters = mockk<FindAllScooters>()

    private val useCase = GetScootersUseCase(findAllScooters)

    @BeforeEach
    fun setUp() = clearAllMocks()

    @Test
    fun `should get all scooters`() {
        // Given
        val scooters = listOf(buildScooter(), buildScooter())
        every { findAllScooters() } returns scooters
        // When
        val result = useCase()
        // Then
        assertThat(result).isEqualTo(scooters)
        verify { findAllScooters() }
    }
}
