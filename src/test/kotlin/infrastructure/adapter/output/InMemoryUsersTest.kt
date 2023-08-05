package infrastructure.adapter.output

import com.example.application.domain.UserId
import com.example.application.domain.UserNotFound
import com.example.fixtures.builders.buildUser
import com.example.fixtures.isLeftWith
import com.example.fixtures.isRightWith
import com.example.infrastructure.adapter.output.InMemoryUsers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class InMemoryUsersTest {

    private val storedUser = buildUser()

    private val inMemoryUsers = InMemoryUsers(listOf(storedUser))

    @Test
    fun `should find all users`() {
        val expected = listOf(storedUser)

        val result = inMemoryUsers.findAll()

        assertThat(result).isEqualTo(expected)
    }

    @Nested
    inner class FindById {

        @Test
        fun `should find a user`() {
            val result = inMemoryUsers.find(storedUser.id)

            assertThat(result).isRightWith(storedUser)
        }

        @Test
        fun `should fail when cannot find user`() {
            val result = inMemoryUsers.find(UserId("some-id"))

            assertThat(result).isLeftWith(UserNotFound)
        }
    }
}