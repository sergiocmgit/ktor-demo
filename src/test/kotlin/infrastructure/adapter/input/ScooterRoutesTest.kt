package infrastructure.adapter.input

import com.example.application.port.input.GetScooters
import com.example.application.port.input.GetScootersResponse
import com.example.application.port.input.LockScooter
import com.example.application.port.input.RunScooter
import com.example.fixtures.builders.buildScooterResponse
import com.example.infrastructure.adapter.input.scooters
import com.example.infrastructure.config.testRoutesModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.install
import io.ktor.server.routing.Routing
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ScooterRoutesTest {

    private val getScooters = mockk<GetScooters>()
    private val runScooter = mockk<RunScooter>()
    private val lockScooter = mockk<LockScooter>()

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `should get all the scooters`() = testApplication {
        appSetup()
        val expected = GetScootersResponse(listOf(buildScooterResponse()))
        every { getScooters() } returns expected

        val response = client.get("/scooters")
        val responseObject = objectMapper.readValue(response.bodyAsText(), GetScootersResponse::class.java)
        assertThat(response.status).isEqualTo(OK)
        assertThat(responseObject).isEqualTo(expected)
    }

    private fun ApplicationTestBuilder.appSetup() = application {
        testRoutesModule()
        install(Routing) {
            scooters(getScooters, runScooter, lockScooter)
        }
    }
}