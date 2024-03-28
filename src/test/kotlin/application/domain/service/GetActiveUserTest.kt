package application.domain.service

import com.example.application.domain.UserId
import com.example.application.domain.UserInvalidStatus
import com.example.application.domain.UserStatus.DEACTIVATED
import com.example.application.domain.service.GetActiveUser
import com.example.application.port.output.UserRepository
import fixtures.builders.DEFAULT_USER_ID
import fixtures.builders.buildUser
import fixtures.isLeftWith
import fixtures.isRightWith
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GetActiveUserTest {

    private val userRepository = mockk<UserRepository>()
    private val getActiveUser = GetActiveUser(userRepository)

    private val userId = UserId(DEFAULT_USER_ID)
    private val user = buildUser(userId = userId.value)

    @Test
    fun `should get an active user`() {
        // Given
        every { userRepository.findBy(userId) } returns user
        // When
        val result = getActiveUser(userId)
        // Then
        assertThat(result).isRightWith(user)
    }

    @Test
    fun `should fail when the user is not active`() {
        // Given
        every { userRepository.findBy(userId) } returns user.copy(status = DEACTIVATED)
        // When
        val result = getActiveUser(userId)
        // Then
        assertThat(result).isLeftWith(UserInvalidStatus)
    }
}