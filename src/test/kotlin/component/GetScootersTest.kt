package component

import com.example.application.port.input.GetScootersResponse
import fixtures.DatabaseUtils.Companion.save
import fixtures.builders.buildScooter
import fixtures.builders.buildScooterResponse
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.testing.testApplication
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.random.Random

class GetScootersTest : ComponentTest() {
    @Test
    fun `should get the scooters`() =
        testApplication {
            appSetup()
            // Given
            val scooterId = Random.nextInt()
            buildScooter(scooterId = scooterId).also(::save)
            val expected = GetScootersResponse(listOf(buildScooterResponse(scooterId = scooterId)))
            // When
            val response = client.get("/scooters")
            val responseObject = objectMapper.readValue(response.bodyAsText(), expected::class.java)
            // Then
            assertThat(response.status).isEqualTo(OK)
            assertThat(responseObject).isEqualTo(expected)
        }
}
