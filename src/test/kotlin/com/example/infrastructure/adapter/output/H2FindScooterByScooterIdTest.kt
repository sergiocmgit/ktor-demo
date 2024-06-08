package com.example.infrastructure.adapter.output

import com.example.application.domain.ScooterNotFound
import com.example.fixtures.DatabaseUtils.Companion.save
import com.example.fixtures.builders.buildScooter
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchException
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class H2FindScooterByScooterIdTest : H2Test {
    private val h2FindScooterByScooterId = H2FindScooterByScooterId()

    private val storedScooter = buildScooter()

    @BeforeEach
    fun beforeEach() {
        transaction { ScooterTable.deleteAll() }
    }

    @Test
    fun `should find a scooter`() {
        // Given
        save(storedScooter)
        // When
        val result = h2FindScooterByScooterId(storedScooter.id)
        // Then
        assertThat(result).isEqualTo(storedScooter)
    }

    @Test
    fun `should fail when cannot find a scooter`() {
        // When
        val result = catchException { h2FindScooterByScooterId(storedScooter.id) }
        // Then
        assertThat(result).isEqualTo(ScooterNotFound)
    }
}
