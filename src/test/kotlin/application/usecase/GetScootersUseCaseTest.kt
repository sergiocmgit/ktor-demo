package application.usecase

import com.example.application.port.input.GetScootersResponse
import com.example.application.port.output.ScooterRepository
import com.example.application.usecase.GetScootersUseCase
import fixtures.builders.buildScooter
import fixtures.builders.buildScooterResponse
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetScootersUseCaseTest {
    private val scooterRepository = mockk<ScooterRepository>()

    private val useCase = GetScootersUseCase(scooterRepository)

    @BeforeEach
    fun setUp() = clearAllMocks()

    @Test
    fun `should get all scooters`() {
        // Given
        val scooters = listOf(buildScooter(), buildScooter())
        val scootersResponse = listOf(buildScooterResponse(), buildScooterResponse())
        val expected = GetScootersResponse(scooters = scootersResponse)
        every { scooterRepository.findAll() } returns scooters
        // When
        val result = useCase()
        // Then
        assertThat(result).isEqualTo(expected)
        verify { scooterRepository.findAll() }
    }
}
