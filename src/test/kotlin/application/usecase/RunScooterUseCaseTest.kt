package application.usecase

import com.example.application.domain.ScooterInvalidStatus
import com.example.application.domain.ScooterRunning
import com.example.application.domain.ScooterStatus.RUNNING
import com.example.application.domain.UserId
import com.example.application.domain.UserInvalidStatus
import com.example.application.domain.UserStatus.DEACTIVATED
import com.example.application.port.input.RunScooterInput
import com.example.application.port.output.ScooterRepository
import com.example.application.port.output.UserRepository
import com.example.application.usecase.RunScooterUseCase
import fixtures.builders.DEFAULT_SCOOTER_ID
import fixtures.builders.DEFAULT_USER_ID
import fixtures.builders.buildScooter
import fixtures.builders.buildUser
import fixtures.isLeftWith
import fixtures.isRightWith
import io.mockk.Ordering.ORDERED
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RunScooterUseCaseTest {
    private val userRepository = mockk<UserRepository>()
    private val scooterRepository = mockk<ScooterRepository>()

    private val useCase = RunScooterUseCase(userRepository, scooterRepository)

    private val userId = DEFAULT_USER_ID
    private val scooterId = DEFAULT_SCOOTER_ID

    @BeforeEach
    fun setUp() = clearAllMocks()

    @Test
    fun `should run scooter`() {
        // Given
        val lockedScooter = buildScooter()
        val runningScooter = lockedScooter.copy(status = RUNNING)
        val expected = ScooterRunning(scooterId)
        every { userRepository.find(UserId(userId)) } returns buildUser()
        every { scooterRepository.find(lockedScooter.id) } returns lockedScooter
        justRun { scooterRepository.update(runningScooter) }
        // When
        val result = useCase(RunScooterInput(scooterId, userId))
        // Then
        assertThat(result).isRightWith(expected)
        verify(ORDERED) {
            userRepository.find(UserId(userId))
            scooterRepository.find(lockedScooter.id)
            scooterRepository.update(runningScooter)
        }
    }

    @Test
    fun `should fail when user is in an invalid status`() {
        // Given
        val user = buildUser(status = DEACTIVATED)
        every { userRepository.find(UserId(userId)) } returns user
        // When
        val result = useCase(RunScooterInput(scooterId, userId))
        // Then
        assertThat(result).isLeftWith(UserInvalidStatus)
    }

    @Test
    fun `should fail when scooter is in an invalid status`() {
        // Given
        val lockedScooter = buildScooter(status = RUNNING)
        every { userRepository.find(UserId(userId)) } returns buildUser()
        every { scooterRepository.find(lockedScooter.id) } returns lockedScooter
        // When
        val result = useCase(RunScooterInput(scooterId, userId))
        // Then
        assertThat(result).isLeftWith(ScooterInvalidStatus)
    }
}
