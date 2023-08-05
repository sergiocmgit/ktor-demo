package application.domain

import com.example.application.domain.Scooter
import com.example.application.domain.ScooterId
import com.example.application.domain.ScooterInvalidStatus
import com.example.application.domain.ScooterStatus.LOCKED
import com.example.application.domain.ScooterStatus.RUNNING
import com.example.application.domain.UserId
import com.example.fixtures.isLeftWith
import com.example.fixtures.isRightWith
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ScooterTest {

    @Nested
    inner class RunScooter {

        private val scooter = Scooter(ScooterId(1), LOCKED, UserId("A"))
        private val lastRider = UserId("B")

        @Test
        fun `should run Scooter`() {
            val expected = scooter.copy(status = RUNNING, lastRider = lastRider)

            val result = scooter.running(lastRider)

            assertThat(result).isRightWith(expected)
        }

        @Test
        fun `should fail when Scooter is not locked`() {
            val scooter = scooter.copy(status = RUNNING)

            val result = scooter.running(lastRider)

            assertThat(result).isLeftWith(ScooterInvalidStatus)
        }
    }

    @Nested
    inner class LockScooter {

        private val userId = UserId("A")
        private val scooter = Scooter(ScooterId(1), RUNNING, userId)

        @Test
        fun `should lock Scooter`() {
            val expected = scooter.copy(status = LOCKED)

            val result = scooter.locked(userId)

            assertThat(result).isRightWith(expected)
        }

        @Test
        fun `should fail when Scooter is not running`() {
            val scooter = scooter.copy(status = LOCKED)

            val result = scooter.locked(userId)

            assertThat(result).isLeftWith(ScooterInvalidStatus)
        }

        @Test
        fun `should fail when UserId was not the Scooter rider`() {
            val userId = UserId("B")

            val result = scooter.locked(userId)

            assertThat(result).isLeftWith(ScooterInvalidStatus)
        }
    }
}
