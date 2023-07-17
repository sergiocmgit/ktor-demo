package application.domain

import com.example.application.domain.Scooter
import com.example.application.domain.ScooterId
import com.example.application.domain.ScooterStatus.LOCKED
import com.example.application.domain.ScooterStatus.RUNNING
import com.example.application.domain.UserId
import com.example.fixtures.isRightWith
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ScooterTest {

    private val scooter = Scooter(
        ScooterId(1),
        LOCKED,
        UserId("A")
    )

    @Test
    fun `should run Scooter`() {
        val userId = UserId("B")
        val expected = scooter.copy(status = RUNNING, lastRider = userId)

        val result = scooter.running(userId)

        assertThat(result).isRightWith(expected)
    }
}