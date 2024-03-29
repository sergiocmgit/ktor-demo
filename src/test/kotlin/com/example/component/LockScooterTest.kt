package com.example.component

import com.example.application.domain.ScooterStatus.RUNNING
import com.example.fixtures.DatabaseUtils.Companion.save
import com.example.fixtures.builders.buildScooter
import com.example.fixtures.builders.buildUser
import com.example.fixtures.builders.randomUserId
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode.Companion.OK
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.random.Random

class LockScooterTest : ComponentTest() {
    @Test
    fun `should lock a scooter`() =
        test {
            // Given
            val userId = randomUserId().also { save(buildUser(userId = it)) }
            val scooterId =
                Random.nextInt()
                    .also {
                        save(
                            buildScooter(
                                scooterId = it,
                                status = RUNNING,
                                lastRider = userId,
                            ),
                        )
                    }
            // When
            val response = client.post("/scooters/$scooterId/lock/$userId")
            // Then
            assertThat(response.status).isEqualTo(OK)
        }
}
