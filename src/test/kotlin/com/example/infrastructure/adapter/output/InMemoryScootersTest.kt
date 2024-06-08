package com.example.infrastructure.adapter.output

import com.example.fixtures.builders.buildScooter
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach

class InMemoryScootersTest : H2Test {
    private val inMemoryScooters = InMemoryScooters()

    private val storedScooter = buildScooter()

    @BeforeEach
    fun beforeEach() {
        transaction { ScooterTable.deleteAll() }
    }

    /*@Test
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
        val result = inMemoryScooters.findBy(storedScooter.id)
        // Then
        assertThat(result).isEqualTo(updatedScooter)
    }*/
}
