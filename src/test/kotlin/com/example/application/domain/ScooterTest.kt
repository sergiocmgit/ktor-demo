package com.example.application.domain

import com.example.application.domain.ScooterStatus.LOCKED
import com.example.application.domain.ScooterStatus.RUNNING
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
            // Given
            val expected = scooter.copy(status = RUNNING, lastRider = lastRider)
            // When
            val result = scooter.running(lastRider)
            // Then
            assertThat(result).isRightWith(expected)
        }

        @Test
        fun `should fail when Scooter is not locked`() {
            // Given
            val scooter = scooter.copy(status = RUNNING)
            // When
            val result = scooter.running(lastRider)
            // Then
            assertThat(result).isLeftWith(ScooterInvalidStatus)
        }
    }

    @Nested
    inner class LockScooter {
        private val userId = UserId("A")
        private val scooter = Scooter(ScooterId(1), RUNNING, userId)

        @Test
        fun `should lock Scooter`() {
            // Given
            val expected = scooter.copy(status = LOCKED)
            // When
            val result = scooter.locked(userId)
            // Then
            assertThat(result).isRightWith(expected)
        }

        @Test
        fun `should fail when Scooter is not running`() {
            // Given
            val scooter = scooter.copy(status = LOCKED)
            // When
            val result = scooter.locked(userId)
            // Then
            assertThat(result).isLeftWith(ScooterInvalidStatus)
        }

        @Test
        fun `should fail when UserId was not the Scooter rider`() {
            // Given
            val userId = UserId("B")
            // When
            val result = scooter.locked(userId)
            // Then
            assertThat(result).isLeftWith(ScooterInvalidStatus)
        }
    }
}
