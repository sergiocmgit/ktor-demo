package com.example.application.usecase

import arrow.core.left
import arrow.core.right
import com.example.application.domain.ScooterInvalidStatus
import com.example.application.domain.ScooterLocked
import com.example.application.domain.ScooterStatus.LOCKED
import com.example.application.domain.ScooterStatus.RUNNING
import com.example.application.domain.UserId
import com.example.application.domain.UserInvalidStatus
import com.example.application.domain.service.GetActiveUser
import com.example.application.port.input.LockScooterInput
import com.example.application.port.output.ScooterRepository
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

class LockScooterUseCaseTest {
    private val getActiveUser = mockk<GetActiveUser>()
    private val scooterRepository = mockk<ScooterRepository>()

    private val useCase = LockScooterUseCase(getActiveUser, scooterRepository)

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
        every { getActiveUser(UserId(userId)) } returns buildUser().right()
        every { scooterRepository.findBy(runningScooter.id) } returns runningScooter
        justRun { scooterRepository.update(lockedScooter) }
        // When
        val result = useCase(LockScooterInput(scooterId, userId))
        // Then
        assertThat(result).isRightWith(expected)
        verify(ORDERED) {
            getActiveUser(UserId(userId))
            scooterRepository.findBy(runningScooter.id)
            scooterRepository.update(lockedScooter)
        }
    }

    @Test
    fun `should fail when the user is in an invalid status`() {
        // Given
        every { getActiveUser(UserId(userId)) } returns UserInvalidStatus.left()
        // When
        val result = useCase(LockScooterInput(scooterId, userId))
        // Then
        assertThat(result).isLeftWith(UserInvalidStatus)
    }

    @Test
    fun `should fail when the scooter is in an invalid status`() {
        // Given
        val lockedScooter = buildScooter(status = LOCKED)
        every { getActiveUser(UserId(userId)) } returns buildUser().right()
        every { scooterRepository.findBy(lockedScooter.id) } returns lockedScooter
        // When
        val result = useCase(LockScooterInput(scooterId, userId))
        // Then
        assertThat(result).isLeftWith(ScooterInvalidStatus)
    }
}