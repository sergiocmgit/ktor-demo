package com.example.infrastructure.adapter.output

import com.example.application.domain.ScooterNotFound
import com.example.application.domain.ScooterStatus.RUNNING
import com.example.application.domain.UserId
import com.example.fixtures.DatabaseUtils.Companion.save
import com.example.fixtures.builders.buildScooter
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchException
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
            val result = inMemoryScooters.findBy(storedScooter.id)
            // Then
            assertThat(result).isEqualTo(storedScooter)
        }

        @Test
        fun `should fail when cannot find a scooter`() {
            // When
            val result = catchException { inMemoryScooters.findBy(storedScooter.id) }
            // Then
            assertThat(result).isEqualTo(ScooterNotFound)
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
        val result = inMemoryScooters.findBy(storedScooter.id)
        // Then
        assertThat(result).isEqualTo(updatedScooter)
    }
}