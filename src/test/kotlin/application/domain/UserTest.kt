package application.domain

import com.example.application.domain.Name
import com.example.application.domain.User
import com.example.application.domain.UserId
import com.example.application.domain.UserInvalidStatus
import com.example.application.domain.UserStatus.ACTIVE
import com.example.application.domain.UserStatus.DEACTIVATED
import com.example.fixtures.isLeftWith
import com.example.fixtures.isRightWith
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserTest {

    @Test
    fun `should check User is active`() {
        val user = User(UserId("A"), Name("Eli"), ACTIVE)

        val result = user.checkIsActive()

        assertThat(result).isRightWith(user)
    }

    @Test
    fun `should check User is not active`() {
        val user = User(UserId("A"), Name("Eli"), DEACTIVATED)

        val result = user.checkIsActive()

        assertThat(result).isLeftWith(UserInvalidStatus)
    }
}
