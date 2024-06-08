package com.example.infrastructure.adapter.output

import com.example.application.domain.ScooterStatus
import com.example.application.domain.UserId
import com.example.fixtures.DatabaseUtils.Companion.findScooterBy
import com.example.fixtures.DatabaseUtils.Companion.save
import com.example.fixtures.builders.buildScooter
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class H2UpdateScooterTest : H2Test {
    private val h2UpdateScooter = H2UpdateScooter()

    private val storedScooter = buildScooter()

    @BeforeEach
    fun beforeEach() {
        transaction { ScooterTable.deleteAll() }
    }

    @Test
    fun `should update a scooter`() {
        // Given
        save(storedScooter)
        val updatedScooter =
            storedScooter.copy(
                status = ScooterStatus.RUNNING,
                lastRider = UserId("some-id"),
            )
        // When
        h2UpdateScooter(updatedScooter)
        val result = findScooterBy(storedScooter.id)
        // Then
        assertThat(result).isEqualTo(updatedScooter)
    }
}
