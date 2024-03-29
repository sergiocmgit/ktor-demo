package com.example.component

import com.example.fixtures.DatabaseUtils.Companion.save
import com.example.fixtures.builders.buildScooter
import com.example.fixtures.builders.buildScooterDto
import com.example.infrastructure.adapter.input.GetScootersResponse
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode.Companion.OK
import kotlin.random.Random
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GetScootersTest : ComponentTest() {
    @Test
    fun `should get the scooters`() =
        test {
            // Given
            val scooterId = Random.nextInt()
            buildScooter(scooterId = scooterId).also(::save)
            // When
            val response = client.get("/scooters")
            val responseObject = objectMapper.readValue(response.bodyAsText(), GetScootersResponse::class.java)
            // Then
            assertThat(response.status).isEqualTo(OK)
            assertThat(responseObject).isEqualTo(GetScootersResponse(listOf(buildScooterDto(scooterId = scooterId))))
        }
}
