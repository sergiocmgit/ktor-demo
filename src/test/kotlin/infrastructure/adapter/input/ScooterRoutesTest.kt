package infrastructure.adapter.input

import arrow.core.right
import com.example.application.port.input.GetScooters
import com.example.application.port.input.GetScootersResponse
import com.example.application.port.input.LockScooter
import com.example.application.port.input.LockScooterRequest
import com.example.application.port.input.RunScooter
import com.example.application.port.input.RunScooterRequest
import com.example.application.port.input.ScooterLocked
import com.example.application.port.input.ScooterRunning
import com.example.fixtures.builders.buildScooterResponse
import com.example.infrastructure.adapter.input.scooters
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import infrastructure.config.testRoutesModule
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.install
import io.ktor.server.routing.Routing
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ScooterRoutesTest : RoutingTest() {
    private val getScooters = mockk<GetScooters>()
    private val runScooter = mockk<RunScooter>()
    private val lockScooter = mockk<LockScooter>()

    private val objectMapper = jacksonObjectMapper()

    @BeforeEach
    fun setUp() = clearAllMocks()

    @Test
    fun `should get all the scooters`() =
        testApplication {
            appSetup()
            // Given
            val expected = GetScootersResponse(listOf(buildScooterResponse()))
            every { getScooters() } returns expected
            // When
            val response = client.get("/scooters")
            val responseObject = objectMapper.readValue(response.bodyAsText(), expected::class.java)
            // Then
            assertThat(response.status).isEqualTo(OK)
            assertThat(responseObject).isEqualTo(expected)
            verify { getScooters() }
        }

    @Test
    fun `should run a scooter`() =
        testApplication {
            appSetup()
            // Given
            val scooterId = 1
            val userId = "A"
            val request = RunScooterRequest(scooterId, userId)
            every { runScooter(request) } returns ScooterRunning(scooterId).right()
            // When
            val response = client.post("/scooters/$scooterId/run/$userId")
            // Then
            assertThat(response.status).isEqualTo(OK)
            verify { runScooter(request) }
        }

    @Test
    fun `should lock a scooter`() =
        testApplication {
            appSetup()
            // Given
            val scooterId = 1
            val userId = "A"
            val request = LockScooterRequest(scooterId, userId)
            every { lockScooter(request) } returns ScooterLocked(scooterId).right()
            // When
            val response = client.post("/scooters/$scooterId/lock/$userId")
            // Then
            assertThat(response.status).isEqualTo(OK)
            verify { lockScooter(request) }
        }

    override fun ApplicationTestBuilder.appSetup() =
        application {
            testRoutesModule()
            install(Routing) {
                scooters(getScooters, runScooter, lockScooter)
            }
        }
}
