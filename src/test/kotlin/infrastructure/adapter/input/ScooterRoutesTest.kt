package infrastructure.adapter.input

import arrow.core.left
import arrow.core.right
import com.example.application.domain.ScooterInvalidStatus
import com.example.application.domain.ScooterLocked
import com.example.application.domain.ScooterRunning
import com.example.application.domain.UserInvalidStatus
import com.example.application.port.input.GetScooters
import com.example.application.port.input.LockScooter
import com.example.application.port.input.LockScooterInput
import com.example.application.port.input.RunScooter
import com.example.application.port.input.RunScooterInput
import com.example.infrastructure.adapter.input.GetScootersResponse
import com.example.infrastructure.adapter.input.scooters
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import fixtures.builders.buildScooter
import fixtures.builders.buildScooterDto
import infrastructure.config.testRoutesModule
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.install
import io.ktor.server.routing.Routing
import io.ktor.server.testing.ApplicationTestBuilder
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class ScooterRoutesTest : RoutingTest() {
    private val getScooters = mockk<GetScooters>()
    private val runScooter = mockk<RunScooter>()
    private val lockScooter = mockk<LockScooter>()

    private val objectMapper = jacksonObjectMapper()

    @BeforeEach
    fun setUp() = clearAllMocks()

    @Test
    fun `should get all the scooters`() =
        test {
            // Given
            every { getScooters() } returns listOf(buildScooter())
            // When
            val response = client.get("/scooters")
            val responseObject = objectMapper.readValue(response.bodyAsText(), GetScootersResponse::class.java)
            // Then
            assertThat(response.status).isEqualTo(OK)
            assertThat(responseObject).isEqualTo(GetScootersResponse(listOf(buildScooterDto())))
            verify { getScooters() }
        }

    @Test
    fun `should run a scooter`() =
        test {
            // Given
            val scooterId = 1
            val userId = "A"
            val input = RunScooterInput(scooterId, userId)
            every { runScooter(input) } returns ScooterRunning(scooterId).right()
            // When
            val response = client.post("/scooters/$scooterId/run/$userId")
            // Then
            assertThat(response.status).isEqualTo(OK)
            verify { runScooter(input) }
        }

    @TestFactory
    fun `should fail to run a scooter`() =
        listOf(ScooterInvalidStatus, UserInvalidStatus).map { error ->
            dynamicTest(error.javaClass.simpleName) {
                test {
                    // Given
                    val scooterId = 1
                    val userId = "A"
                    val input = RunScooterInput(scooterId, userId)
                    every { runScooter(input) } returns error.left()
                    // When
                    val response = client.post("/scooters/$scooterId/run/$userId")
                    // Then
                    assertThat(response.status).isEqualTo(BadRequest)
                }
            }
        }

    @Test
    fun `should lock a scooter`() =
        test {
            // Given
            val scooterId = 1
            val userId = "A"
            val input = LockScooterInput(scooterId, userId)
            every { lockScooter(input) } returns ScooterLocked(scooterId).right()
            // When
            val response = client.post("/scooters/$scooterId/lock/$userId")
            // Then
            assertThat(response.status).isEqualTo(OK)
            verify { lockScooter(input) }
        }

    @TestFactory
    fun `should fail to lock a scooter`() =
        listOf(ScooterInvalidStatus, UserInvalidStatus).map { error ->
            dynamicTest(error.javaClass.simpleName) {
                test {
                    // Given
                    val scooterId = 1
                    val userId = "A"
                    val input = LockScooterInput(scooterId, userId)
                    every { lockScooter(input) } returns error.left()
                    // When
                    val response = client.post("/scooters/$scooterId/lock/$userId")
                    // Then
                    assertThat(response.status).isEqualTo(BadRequest)
                }
            }
        }

    override fun ApplicationTestBuilder.appSetup() =
        application {
            testRoutesModule()
            install(Routing) {
                scooters(getScooters, runScooter, lockScooter)
            }
        }
}
