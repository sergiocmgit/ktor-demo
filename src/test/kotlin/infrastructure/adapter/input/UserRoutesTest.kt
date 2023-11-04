package infrastructure.adapter.input

import com.example.application.port.input.GetUsers
import com.example.application.port.input.GetUsersResponse
import com.example.fixtures.builders.buildUserResponse
import com.example.infrastructure.adapter.input.users
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import infrastructure.config.testRoutesModule
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.install
import io.ktor.server.routing.Routing
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserRoutesTest : RoutingTest() {
    private val getUsers = mockk<GetUsers>()

    private val objectMapper = jacksonObjectMapper()

    @BeforeEach
    fun setUp() = clearAllMocks()

    @Test
    fun `should get all the users`() =
        testApplication {
            appSetup()
            // Given
            val expected = GetUsersResponse(listOf(buildUserResponse()))
            every { getUsers() } returns expected
            // When
            val response = client.get("/users")
            val responseObject = objectMapper.readValue(response.bodyAsText(), expected::class.java)
            // Then
            assertThat(response.status).isEqualTo(OK)
            assertThat(responseObject).isEqualTo(expected)
        }

    override fun ApplicationTestBuilder.appSetup() =
        application {
            testRoutesModule()
            install(Routing) {
                users(getUsers)
            }
        }
}
