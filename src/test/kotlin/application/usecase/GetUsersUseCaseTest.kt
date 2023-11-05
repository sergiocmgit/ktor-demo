package application.usecase

import com.example.application.port.input.GetUsersResponse
import com.example.application.port.output.UserRepository
import com.example.application.usecase.GetUsersUseCase
import fixtures.builders.buildUser
import fixtures.builders.buildUserResponse
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
        val users = listOf(buildUser(), buildUser())
        val usersResponse = listOf(buildUserResponse(), buildUserResponse())
        val expected = GetUsersResponse(users = usersResponse)
        every { userRepository.findAll() } returns users
        // When
        val result = useCase()
        // Then
        assertThat(result).isEqualTo(expected)
        verify { userRepository.findAll() }
    }
}
