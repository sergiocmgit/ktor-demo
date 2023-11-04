package infrastructure.adapter.output

import com.example.application.domain.ScooterNotFound
import com.example.application.domain.ScooterStatus.RUNNING
import com.example.application.domain.UserId
import com.example.fixtures.builders.buildScooter
import com.example.fixtures.isLeftWith
import com.example.fixtures.isRightWith
import com.example.infrastructure.adapter.output.InMemoryScooters
import com.example.infrastructure.adapter.output.ScooterTable
import fixtures.DatabaseUtils.Companion.save
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class InMemoryScootersTest : InMemoryTest {
    private val inMemoryScooters = InMemoryScooters()

    private val storedScooter = buildScooter()

    @BeforeEach
    fun beforeEach() {
        transaction { ScooterTable.deleteAll() }
    }

    @Test
    fun `should find all scooters`() {
        // Given
        save(storedScooter)
        val expected = listOf(storedScooter)
        // When
        val result = inMemoryScooters.findAll()
        // Then
        assertThat(result).isEqualTo(expected)
    }

    @Nested
    inner class FindById {
        @Test
        fun `should find a scooter`() {
            // Given
            save(storedScooter)
            // When
            val result = inMemoryScooters.find(storedScooter.id)
            // Then
            assertThat(result).isRightWith(storedScooter)
        }

        @Test
        fun `should fail when cannot find a scooter`() {
            // When
            val result = inMemoryScooters.find(storedScooter.id)
            // Then
            assertThat(result).isLeftWith(ScooterNotFound)
        }
    }

    @Test
    fun `should update a scooter`() {
        // Given
        save(storedScooter)
        val updatedScooter =
            storedScooter.copy(
                status = RUNNING,
                lastRider = UserId("some-id"),
            )
        // When
        inMemoryScooters.update(updatedScooter)
        val result = inMemoryScooters.find(storedScooter.id)
        // Then
        assertThat(result).isRightWith(updatedScooter)
    }
}
