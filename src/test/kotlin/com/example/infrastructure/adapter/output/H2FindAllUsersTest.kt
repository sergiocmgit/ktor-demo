package com.example.infrastructure.adapter.output

import com.example.fixtures.DatabaseUtils.Companion.save
import com.example.fixtures.builders.buildUser
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class H2FindAllUsersTest : InMemoryTest {
    private val h2FindAllUsers = H2FindAllUsers()

    private val storedUser = buildUser()

    @BeforeEach
    fun beforeEach() {
        transaction { UserTable.deleteAll() }
    }

    @Test
    fun `should find all users`() {
        // Given
        save(storedUser)
        val expected = listOf(storedUser)
        // When
        val result = h2FindAllUsers()
        // Then
        assertThat(result).isEqualTo(expected)
    }
}
