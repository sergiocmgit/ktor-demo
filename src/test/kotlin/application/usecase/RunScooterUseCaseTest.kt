package application.usecase

import arrow.core.left
import arrow.core.right
import com.example.application.domain.ScooterId
import com.example.application.domain.ScooterInvalidStatus
import com.example.application.domain.ScooterNotFound
import com.example.application.domain.ScooterStatus.RUNNING
import com.example.application.domain.UserId
import com.example.application.domain.UserInvalidStatus
import com.example.application.domain.UserNotFound
import com.example.application.domain.UserStatus.DEACTIVATED
import com.example.application.port.input.RunScooterRequest
import com.example.application.port.input.ScooterRunning
import com.example.application.port.output.ScooterRepository
import com.example.application.port.output.UserRepository
import com.example.application.usecase.RunScooterUseCase
import com.example.fixtures.builders.DEFAULT_SCOOTER_ID
import com.example.fixtures.builders.DEFAULT_USER_ID
import com.example.fixtures.builders.buildScooter
import com.example.fixtures.builders.buildUser
import com.example.fixtures.isLeftWith
import com.example.fixtures.isRightWith
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
        every { userRepository.find(UserId(userId)) } returns buildUser().right()
        every { scooterRepository.find(lockedScooter.id) } returns lockedScooter.right()
        justRun { scooterRepository.update(runningScooter) }
        // When
        val result = useCase(RunScooterRequest(scooterId, userId))
        // Then
        assertThat(result).isRightWith(expected)
        verify(ORDERED) {
            userRepository.find(UserId(userId))
            scooterRepository.find(lockedScooter.id)
            scooterRepository.update(runningScooter)
        }
    }

    @Test
    fun `should fail when cannot find user`() {
        // Given
        every { userRepository.find(UserId(userId)) } returns UserNotFound.left()
        // When
        val result = useCase(RunScooterRequest(scooterId, userId))
        // Then
        assertThat(result).isLeftWith(UserNotFound)
    }

    @Test
    fun `should fail when user is in an invalid status`() {
        // Given
        val user = buildUser(status = DEACTIVATED)
        every { userRepository.find(UserId(userId)) } returns user.right()
        // When
        val result = useCase(RunScooterRequest(scooterId, userId))
        // Then
        assertThat(result).isLeftWith(UserInvalidStatus)
    }

    @Test
    fun `should fail when cannot find scooter`() {
        // Given
        every { userRepository.find(UserId(userId)) } returns buildUser().right()
        every { scooterRepository.find(ScooterId(scooterId)) } returns ScooterNotFound.left()
        // When
        val result = useCase(RunScooterRequest(scooterId, userId))
        // Then
        assertThat(result).isLeftWith(ScooterNotFound)
    }

    @Test
    fun `should fail when scooter is in an invalid status`() {
        // Given
        val lockedScooter = buildScooter(status = RUNNING)
        every { userRepository.find(UserId(userId)) } returns buildUser().right()
        every { scooterRepository.find(lockedScooter.id) } returns lockedScooter.right()
        // When
        val result = useCase(RunScooterRequest(scooterId, userId))
        // Then
        assertThat(result).isLeftWith(ScooterInvalidStatus)
    }
}
