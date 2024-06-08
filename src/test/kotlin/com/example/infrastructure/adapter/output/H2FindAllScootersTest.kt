package com.example.infrastructure.adapter.output

import com.example.fixtures.DatabaseUtils.Companion.save
import com.example.fixtures.builders.buildScooter
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class H2FindAllScootersTest : H2Test {
    private val h2FindAllScooters = H2FindAllScooters()

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
        val result = h2FindAllScooters()
        // Then
        assertThat(result).isEqualTo(expected)
    }
}
