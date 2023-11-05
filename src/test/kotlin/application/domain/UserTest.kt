package application.domain

import com.example.application.domain.Name
import com.example.application.domain.User
import com.example.application.domain.UserId
import com.example.application.domain.UserInvalidStatus
import com.example.application.domain.UserStatus.ACTIVE
import com.example.application.domain.UserStatus.DEACTIVATED
import fixtures.isLeftWith
import fixtures.isRightWith
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserTest {
    @Test
    fun `should check User is active`() {
        // Given
        val user = User(UserId("A"), Name("Eli"), ACTIVE)
        // When
        val result = user.checkIsActive()
        // Then
        assertThat(result).isRightWith(user)
    }

    @Test
    fun `should check User is not active`() {
        // Given
        val user = User(UserId("A"), Name("Eli"), DEACTIVATED)
        // When
        val result = user.checkIsActive()
        // Then
        assertThat(result).isLeftWith(UserInvalidStatus)
    }
}
