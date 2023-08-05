package infrastructure.adapter.output

import com.example.application.domain.ScooterId
import com.example.application.domain.ScooterNotFound
import com.example.application.domain.ScooterStatus.LOCKED
import com.example.application.domain.ScooterStatus.RUNNING
import com.example.fixtures.builders.buildScooter
import com.example.fixtures.isLeftWith
import com.example.fixtures.isRightWith
import com.example.infrastructure.adapter.output.InMemoryScooters
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class InMemoryScootersTest {

    private val storedScooter = buildScooter()

    private lateinit var inMemoryScooters: InMemoryScooters

    @BeforeEach
    fun setUp() {
        inMemoryScooters = InMemoryScooters(mutableListOf(storedScooter))
    }

    @Test
    fun `should find all scooters`() {
        val expected = listOf(storedScooter)

        val result = inMemoryScooters.findAll()

        assertThat(result).isEqualTo(expected)
    }

    @Nested
    inner class FindById {

        @Test
        fun `should find a scooter`() {
            val result = inMemoryScooters.find(storedScooter.id)

            assertThat(result).isRightWith(storedScooter)
        }

        @Test
        fun `should fail when cannot find a scooter`() {
            val result = inMemoryScooters.find(ScooterId(2))

            assertThat(result).isLeftWith(ScooterNotFound)
        }
    }

    @Test
    fun `should update a scooter`() {
        assertThat(storedScooter.status).isEqualTo(LOCKED)
        val updatedScooter = storedScooter.copy(status = RUNNING)

        inMemoryScooters.update(updatedScooter)
        val result = inMemoryScooters.find(storedScooter.id)

        assertThat(result).isRightWith(updatedScooter)
    }
}