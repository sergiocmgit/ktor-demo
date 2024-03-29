package com.example.component

import com.example.application.domain.ScooterStatus.LOCKED
import com.example.fixtures.DatabaseUtils.Companion.save
import com.example.fixtures.builders.buildScooter
import com.example.fixtures.builders.buildUser
import com.example.fixtures.builders.randomUserId
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode.Companion.OK
import kotlin.random.Random
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RunScooterTest : ComponentTest() {
    @Test
    fun `should run a scooter`() =
        test {
            // Given
            val userId = randomUserId().also { save(buildUser(userId = it)) }
            val scooterId = Random.nextInt().also { save(buildScooter(scooterId = it, status = LOCKED)) }
            // When
            val response = client.post("/scooters/$scooterId/run/$userId")
            // Then
            assertThat(response.status).isEqualTo(OK)
        }
}
