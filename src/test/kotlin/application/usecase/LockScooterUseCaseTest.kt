package application.usecase

import arrow.core.left
import arrow.core.right
import com.example.application.domain.ScooterId
import com.example.application.domain.ScooterInvalidStatus
import com.example.application.domain.ScooterNotFound
import com.example.application.domain.ScooterStatus.LOCKED
import com.example.application.domain.ScooterStatus.RUNNING
import com.example.application.domain.UserId
import com.example.application.domain.UserInvalidStatus
import com.example.application.domain.UserNotFound
import com.example.application.domain.UserStatus
import com.example.application.port.input.LockScooterRequest
import com.example.application.port.input.ScooterLocked
import com.example.application.port.output.ScooterRepository
import com.example.application.port.output.UserRepository
import com.example.application.usecase.LockScooterUseCase
import fixtures.builders.DEFAULT_USER_ID
import fixtures.builders.buildUser
import fixtures.isLeftWith
import fixtures.isRightWith
import fixtures.builders.DEFAULT_SCOOTER_ID
import fixtures.builders.buildScooter
import io.mockk.Ordering.ORDERED
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LockScooterUseCaseTest {
    private val userRepository = mockk<UserRepository>()
    private val scooterRepository = mockk<ScooterRepository>()

    private val useCase = LockScooterUseCase(userRepository, scooterRepository)

    private val userId = DEFAULT_USER_ID
    private val scooterId = DEFAULT_SCOOTER_ID

    @BeforeEach
    fun setUp() = clearAllMocks()

    @Test
    fun `should lock scooter`() {
        // Given
        val runningScooter = buildScooter(status = RUNNING)
        val lockedScooter = runningScooter.copy(status = LOCKED)
        val expected = ScooterLocked(scooterId)
        every { userRepository.find(UserId(userId)) } returns buildUser().right()
        every { scooterRepository.find(runningScooter.id) } returns runningScooter.right()
        justRun { scooterRepository.update(lockedScooter) }
        // When
        val result = useCase(LockScooterRequest(scooterId, userId))
        // Then
        assertThat(result).isRightWith(expected)
        verify(ORDERED) {
            userRepository.find(UserId(userId))
            scooterRepository.find(runningScooter.id)
            scooterRepository.update(lockedScooter)
        }
    }

    @Test
    fun `should fail when cannot find user`() {
        // Given
        every { userRepository.find(UserId(userId)) } returns UserNotFound.left()
        // When
        val result = useCase(LockScooterRequest(scooterId, userId))
        // Then
        assertThat(result).isLeftWith(UserNotFound)
    }

    @Test
    fun `should fail when user is in an invalid status`() {
        // Given
        val user = buildUser(status = UserStatus.DEACTIVATED)
        every { userRepository.find(UserId(userId)) } returns user.right()
        // When
        val result = useCase(LockScooterRequest(scooterId, userId))
        // Then
        assertThat(result).isLeftWith(UserInvalidStatus)
    }

    @Test
    fun `should fail when cannot find scooter`() {
        // Given
        every { userRepository.find(UserId(userId)) } returns buildUser().right()
        every { scooterRepository.find(ScooterId(scooterId)) } returns ScooterNotFound.left()
        // When
        val result = useCase(LockScooterRequest(scooterId, userId))
        // Then
        assertThat(result).isLeftWith(ScooterNotFound)
    }

    @Test
    fun `should fail when scooter is in an invalid status`() {
        // Given
        val lockedScooter = buildScooter(status = LOCKED)
        every { userRepository.find(UserId(userId)) } returns buildUser().right()
        every { scooterRepository.find(lockedScooter.id) } returns lockedScooter.right()
        // When
        val result = useCase(LockScooterRequest(scooterId, userId))
        // Then
        assertThat(result).isLeftWith(ScooterInvalidStatus)
    }
}
